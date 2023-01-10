package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.exception.ProcessNotCompletedException;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.AlreadySubmitException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FinalSubmitService {

    private final ApplicationFacade applicationFacade;

    private final StatusFacade statusFacade;

    private final EntryInfoFacade entryInfoFacade;

    @Transactional
    public void execute() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();

        if (entryInfo.hasEmptyInfo() || checkApplication(entryInfo))
            throw ProcessNotCompletedException.EXCEPTION;

        Status status = statusFacade.getStatusByReceiptCode(entryInfo.getReceiptCode());

        if (Boolean.TRUE.equals(status.getIsSubmitted()))
            throw AlreadySubmitException.EXCEPTION;

        status.isSubmitToTrue();
    }

    private boolean checkApplication(EntryInfo entryInfo) {
        Application application = applicationFacade
                .getApplication(entryInfo.getReceiptCode(), entryInfo.getEducationalStatus());

        return application.hasEmptyInfo();
    }

}
