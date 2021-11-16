package kr.hs.entrydsm.rollsroyce.domain.user.facade;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCode;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCodeLimit;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeLimitRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.AuthCodeAlreadyVerifiedException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.AuthCodeRequestOverLimitException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.InvalidAuthCodeException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UnprovenAuthCodeException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAuthCodeFacade {

    @Value("${auth.code.limit}")
    private long authCodeLimit;

    @Value("${auth.code.limitExp}")
    private long authCodeLimitTTL;

    private final UserFacade userFacade;
    private final AuthCodeRepository authCodeRepository;
    private final AuthCodeLimitRepository authCodeLimitRepository;

    public AuthCode getAuthCodeById(String email) {
        return authCodeRepository.findById(email)
                .orElseThrow(() -> InvalidAuthCodeException.EXCEPTION);
    }

    public boolean isAlreadyVerified(boolean isVerified) {
        if(isVerified) {
            throw AuthCodeAlreadyVerifiedException.EXCEPTION;
        }

        return true;
    }

    public boolean checkVerified(boolean isVerified) {
        if(!isVerified) {
            throw UnprovenAuthCodeException.EXCEPTION;
        }

        return true;
    }

    public boolean checkCount(int count) {
        if(count >= authCodeLimit) {
            throw AuthCodeRequestOverLimitException.EXCEPTION;
        }

        return true;
    }

    public boolean compareCode(String reqCode, String code) {
        return reqCode.equals(code);
    }

    public String getRandomCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    public boolean isOverLimit(String email) {
        authCodeLimitRepository.findById(email)
                .filter(limit -> checkCount(limit.getCount()))
                .map(AuthCodeLimit::addCount)
                .or(() -> Optional.of(authCodeLimitRepository.save(AuthCodeLimit.builder()
                        .email(email)
                        .count(1)
                        .ttl(authCodeLimitTTL * 1000)
                        .build())));

        return true;
    }

    public boolean checkFilter(String email) {
        return isOverLimit(email) && userFacade.isAlreadyExists(email);
    }

}
