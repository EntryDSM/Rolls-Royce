package kr.hs.entrydsm.rollsroyce.domain.user.facade;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCode;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCodeLimit;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeLimitRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.Action;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.*;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserAuthCodeFacade {

    private final UserFacade userFacade;
    private final AuthCodeRepository authCodeRepository;
    private final AuthCodeLimitRepository authCodeLimitRepository;
    private final UserRepository userRepository;
    private final SESUtil sesUtil;

    @Value("${auth.code.limit}")
    private long authCodeLimit;
    @Value("${auth.code.limitExp}")
    private long authCodeLimitTTL;

    public AuthCode getAuthCodeById(String email) {
        return authCodeRepository.findById(email)
                .orElseThrow(() -> InvalidAuthCodeException.EXCEPTION);
    }

    public AuthCode getAuthCodeByIdOrCreate(String email, String code) {
        return authCodeRepository.findById(email)
                .orElseGet(() -> buildAuthCode(email, code, authCodeLimitTTL));
    }

    public boolean isAlreadyVerified(boolean isVerified) {
        if (isVerified) {
            throw AuthCodeAlreadyVerifiedException.EXCEPTION;
        }

        return true;
    }

    public boolean checkVerified(boolean isVerified) {
        if (!isVerified) {
            throw UnVerifiedAuthCodeException.EXCEPTION;
        }

        return true;
    }

    public boolean checkCount(int count) {
        if (count >= authCodeLimit) {
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

    public void isOverLimit(String email) {
        authCodeLimitRepository.findById(email)
                .filter(limit -> checkCount(limit.getCount()))
                .map(AuthCodeLimit::addCount)
                .map(authCodeLimitRepository::save)
                .orElseGet(() -> authCodeLimitRepository.save(AuthCodeLimit.builder()
                        .email(email)
                        .count(1)
                        .ttl(authCodeLimitTTL)
                        .build()));
    }

    public void checkFilter(String email) {
        isOverLimit(email);
        if (userFacade.isAlreadyExists(email) && isVerified(email)) {
            throw AuthCodeAlreadyVerifiedException.EXCEPTION;
        }
    }

    public void sendEmail(String email, Action action, String templateName) {
        String code = getRandomCode();
        AuthCode authCode = getAuthCodeByIdOrCreate(email, code);

        if (Action.PASSWORD.equals(action)) {
            checkPasswordEmailFilter(email);
        } else {
            checkFilter(email);
        }

        Map<String, String> params = new HashMap<>();
        params.put("code", code);

        sesUtil.sendMessage(email, templateName, params);

        authCode.updateAuthCode(code, authCodeLimitTTL);
    }

    public void checkPasswordEmailFilter(String email) {
        isOverLimit(email);
        if (userRepository.findByEmail(email).isEmpty()) {
            throw UserNotFoundException.EXCEPTION;
        }
        if (isVerified(email)) {
            throw UnVerifiedAuthCodeException.EXCEPTION;
        }
    }

    public AuthCode buildAuthCode(String email, String code, Long authCodeTTL) {
        return authCodeRepository.save(AuthCode.builder()
                .email(email)
                .code(code)
                .isVerified(false)
                .ttl(authCodeTTL)
                .build());
    }

    public boolean isVerified(String email) {
        return getAuthCodeById(email).isVerified();
    }

}
