package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.exception.ProcessNotCompletedException;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.AlreadySubmitException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class FinalSubmitService {

    private final ApplicationFacade applicationFacade;

    private final StatusFacade statusFacade;

    private final UserFacade userFacade;

    @Transactional
    public void execute() {
        User user = userFacade.getCurrentUser();

        if (user.hasEmptyInfo() || checkApplication(user)) throw ProcessNotCompletedException.EXCEPTION;

        Status status = statusFacade.getStatusByReceiptCode(user.getReceiptCode());

        if (Boolean.TRUE.equals(status.getIsSubmitted())) throw AlreadySubmitException.EXCEPTION;

        status.isSubmitToTrue();
    }

    private boolean checkApplication(User user) {
        Application application = applicationFacade.getApplication(user.getReceiptCode(), user.getEducationalStatus());

        return application.hasEmptyInfo();
    }
}
