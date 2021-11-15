package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCode;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCodeLimit;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeLimitRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SendEmailRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SendAuthCodeService {

    @Value("auth.code.exp")
    private long authCodeTTL;

    @Value("auth.code.limitExp")
    private long authCodeLimitTTL;

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

        authCodeRepository.findById(email)
                .filter(s -> isOverLimit(email))
                .filter(s -> userFacade.isAlreadyExists(email))
                .filter(s -> sesUtil.sendMessage(email, "RollsRoyceEmailTemplate", params))
                .map(authCode -> authCode.updateAuthCode(code, authCodeTTL * 1000))
                .orElseGet(() -> authCodeRepository.save(AuthCode.builder()
                        .email(email)
                        .code(code)
                        .isVerified(false)
                        .ttl(authCodeTTL * 1000)
                        .build())
                );
    }

    private boolean isOverLimit(String email) {
        authCodeLimitRepository.findById(email)
                .filter(limit -> authCodeFacade.checkCount(limit.getCount()))
                .map(AuthCodeLimit::addCount)
                .or(() -> Optional.of(authCodeLimitRepository.save(AuthCodeLimit.builder()
                        .email(email)
                        .count(1)
                        .ttl(authCodeLimitTTL * 1000)
                        .build())));

        return true;
    }

}
