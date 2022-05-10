package kr.hs.entrydsm.rollsroyce.domain.user.service;

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
public class SendPasswordAuthCodeService {

    private final SESUtil sesUtil;
    private final UserAuthCodeFacade authCodeFacade;
    private final AuthCodeRepository authCodeRepository;

    @Value("${auth.code.exp}")
    private long authCodeTTL;

    @Transactional
    public void execute(SendEmailRequest request, String templateName) {
        String email = request.getEmail();
        String code = authCodeFacade.getRandomCode();

        Map<String, String> params = new HashMap<>();
        params.put("code", code);

        authCodeRepository.findById(email)
                .or(() -> authCodeFacade.buildAuthCode(email, code, authCodeTTL))
                .filter(s -> authCodeFacade.checkPasswordEmailFilter(email))
                .filter(s -> sesUtil.sendMessage(email, templateName, params))
                .map(authCode -> authCode.updateAuthCode(code, authCodeTTL * 1000))
                .map(authCodeRepository::save);
    }
}
