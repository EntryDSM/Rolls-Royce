package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.Action;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SendEmailAuthCodeService {

    private final UserAuthCodeFacade authCodeFacade;

    @Transactional
    public void execute(SendEmailRequest request, String templateName) {

        authCodeFacade.sendEmail(request.getEmail(), Action.EMAIL, templateName);

    }

}
