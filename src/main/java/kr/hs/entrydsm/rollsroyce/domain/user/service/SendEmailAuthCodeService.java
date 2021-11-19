package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCode;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SendEmailRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SendEmailAuthCodeService {

    @Value("${auth.code.exp}")
    private long authCodeTTL;

    private final SESUtil sesUtil;
    private final UserAuthCodeFacade authCodeFacade;
    private final AuthCodeRepository authCodeRepository;

    @Transactional
    public void execute(SendEmailRequest request) {
        String email = request.getEmail();
        String code = authCodeFacade.getRandomCode();

        Map<String, String> params = new HashMap<>();
        params.put("code", code);

        authCodeRepository.findById(email)
                .filter(s -> authCodeFacade.checkFilter(email))
                .filter(s -> sesUtil.sendMessage(email, "MunchkinEmailTemplate", params))
                .map(authCode -> authCode.updateAuthCode(code, authCodeTTL * 1000))
                .orElseGet(() -> authCodeRepository.save(AuthCode.builder()
                        .email(email)
                        .code(code)
                        .isVerified(false)
                        .ttl(authCodeTTL * 1000)
                        .build())
                );
    }

}
