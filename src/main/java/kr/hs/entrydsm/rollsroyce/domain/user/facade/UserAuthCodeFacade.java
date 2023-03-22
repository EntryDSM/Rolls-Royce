package kr.hs.entrydsm.rollsroyce.domain.user.facade;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCode;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCodeLimit;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeLimitRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.Action;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.AuthCodeAlreadyVerifiedException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.AuthCodeRequestOverLimitException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.InvalidAuthCodeException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UnVerifiedAuthCodeException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;

@RequiredArgsConstructor
@Component
public class UserAuthCodeFacade {

    private final AuthCodeLimitRepository authCodeLimitRepository;
    private final AuthCodeRepository authCodeRepository;

    private final SESUtil sesUtil;

    private final UserFacade userFacade;
    private final UserRepository userRepository;

    @Value("${auth.code.exp}") private Long authCodeTTL;

    @Value("${auth.code.limit}") private long authCodeLimit;

    @Value("${auth.code.limitExp}") private long authCodeLimitTTL;

    public AuthCode getAuthCodeById(String email) {
        return authCodeRepository.findById(email).orElseThrow(() -> InvalidAuthCodeException.EXCEPTION);
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

    public boolean compareCode(String reqCode, String code) {
        return reqCode.equals(code);
    }

    public void sendEmail(String email, Action action, String templateName) {
        String code = getRandomCode();
        AuthCode authCode = getAuthCodeByIdOrCreate(email, code);

        isOverLimit(email);
        if (Action.UPDATE_PASSWORD.equals(action)) {
            checkPasswordEmailFilter(email);
        } else {
            checkFilter(email);
        }

        Map<String, String> params = new HashMap<>();
        params.put("code", code);

        sesUtil.sendMessage(email, templateName, params);

        authCode.updateAuthCode(code, authCodeTTL);
    }

    private String getRandomCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    private AuthCode getAuthCodeByIdOrCreate(String email, String code) {
        return authCodeRepository.findById(email).orElseGet(() -> buildAuthCode(email, code));
    }

    private void isOverLimit(String email) {
        authCodeLimitRepository
                .findById(email)
                .filter(limit -> checkCount(limit.getCount()))
                .map(limit -> limit.addCount(authCodeLimitTTL))
                .map(authCodeLimitRepository::save)
                .orElseGet(() -> authCodeLimitRepository.save(AuthCodeLimit.builder()
                        .email(email)
                        .count(1)
                        .ttl(authCodeLimitTTL)
                        .build()));
    }

    private boolean checkCount(int count) {
        if (count >= authCodeLimit) {
            throw AuthCodeRequestOverLimitException.EXCEPTION;
        }

        return true;
    }

    private void checkPasswordEmailFilter(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            throw UserNotFoundException.EXCEPTION;
        }
        if (isVerified(email)) {
            throw UnVerifiedAuthCodeException.EXCEPTION;
        }
    }

    private void checkFilter(String email) {
        if (userFacade.isAlreadyExists(email) && isVerified(email)) {
            throw AuthCodeAlreadyVerifiedException.EXCEPTION;
        }
    }

    private AuthCode buildAuthCode(String email, String code) {
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
