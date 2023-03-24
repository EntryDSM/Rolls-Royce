package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.exception.ProcessNotCompletedException;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.AlreadySubmitException;

@RequiredArgsConstructor
@Service
public class FinalSubmitService {

    private final ApplicationFacade applicationFacade;

    private final StatusFacade statusFacade;

    private final EntryInfoFacade entryInfoFacade;

    @Transactional
    public void execute() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();

        if (entryInfo.hasEmptyInfo() || checkApplication(entryInfo)) throw ProcessNotCompletedException.EXCEPTION;

        Status status = statusFacade.getStatusByReceiptCode(entryInfo.getReceiptCode());

        if (Boolean.TRUE.equals(status.getIsSubmitted())) throw AlreadySubmitException.EXCEPTION;

        status.isSubmitToTrue();
    }

    private boolean checkApplication(EntryInfo entryInfo) {
        Application application =
                applicationFacade.getApplication(entryInfo.getReceiptCode(), entryInfo.getEducationalStatus());

        return application.hasEmptyInfo();
    }
}
