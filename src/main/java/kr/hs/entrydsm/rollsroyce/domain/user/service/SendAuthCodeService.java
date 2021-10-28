package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCode;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserAlreadyExistsException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SendEmailRequest;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtProperties;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SendAuthCodeService {

    private final SESUtil sesUtil;
    private final UserFacade userFacade;
    private final JwtProperties jwtProperties;
    private final UserAuthCodeFacade authCodeFacade;
    private final AuthCodeRepository authCodeRepository;

    @Transactional
    public void execute(SendEmailRequest request) {
        String email = request.getEmail();
        String code = authCodeFacade.getRandomCode();

        Map<String, String> params = new HashMap<>();
        params.put("code", code);

        if(userFacade.isAlreadyExists(email)) {
            throw UserAlreadyExistsException.EXCEPTION;
        }

        authCodeRepository.findById(request.getEmail())
                .filter(authcode -> sesUtil.sendMessage(email, "RollsRoyceEmailTemplate", params))
                .map(authCode -> authCode.updateAuthCode(code, jwtProperties.getRefreshExp()))
                .orElseGet(() -> authCodeRepository.save(AuthCode.builder()
                        .email(email)
                        .code(code)
                        .isVerified(false)
                        .ttl(jwtProperties.getRefreshExp())
                        .build()))  ;
    }

}
