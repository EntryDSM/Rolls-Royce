package kr.hs.entrydsm.rollsroyce.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.Action;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SendEmailRequest;

@RequiredArgsConstructor
@Service
public class SendEmailAuthCodeService {

    private final UserAuthCodeFacade authCodeFacade;

    @Transactional
    public void execute(SendEmailRequest request, String templateName) {

        authCodeFacade.sendEmail(request.getEmail(), Action.SIGN_UP, templateName);
    }
}
