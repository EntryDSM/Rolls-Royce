package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCode;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCodeLimit;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeLimitRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.AuthCodeRequestOverLimitException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserAlreadyExistsException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SendEmailRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SendAuthCodeService {

    private static final long authCodeTTL = 3 * 60;
    private static final long authCodeLimit = 5;

    private final SESUtil sesUtil;
    private final UserFacade userFacade;
    private final UserAuthCodeFacade authCodeFacade;
    private final AuthCodeRepository authCodeRepository;
    private final AuthCodeLimitRepository authCodeLimitRepository;

    @Transactional
    public void execute(SendEmailRequest request) {
        String email = request.getEmail();
        String code = authCodeFacade.getRandomCode();

        Map<String, String> params = new HashMap<>();
        params.put("code", code);

        if(isOverLimit(email))
            throw AuthCodeRequestOverLimitException.EXCEPTION;

        if(userFacade.isAlreadyExists(email))
            throw UserAlreadyExistsException.EXCEPTION;

        authCodeRepository.findById(email)
                .filter(authcode -> sesUtil.sendMessage(email, "RollsRoyceEmailTemplate", params))
                .map(authCode -> authCode.updateAuthCode(code, authCodeTTL))
                .orElseGet(() -> authCodeRepository.save(AuthCode.builder()
                            .email(email)
                            .code(code)
                            .isVerified(false)
                            .ttl(authCodeTTL)
                            .build()));
    }

    private boolean isOverLimit(String email) {
        return !authCodeLimitRepository.findById(email)
                .filter(limit -> limit.getCount() < authCodeLimit)
                .map(authCodeLimit -> authCodeLimit.updateAuthCode(authCodeTTL))
                .or(() -> Optional.of(authCodeLimitRepository.save(AuthCodeLimit.builder()
                        .email(email)
                        .count(1)
                        .ttl(authCodeTTL)
                        .build())))
                .isPresent();
    }

}
