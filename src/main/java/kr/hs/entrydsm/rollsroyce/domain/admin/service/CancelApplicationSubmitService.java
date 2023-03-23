package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;

@RequiredArgsConstructor
@Service
public class CancelApplicationSubmitService {

    private final StatusFacade statusFacade;

    private final EntryInfoFacade entryInfoFacade;

    @Transactional
    public void execute(long receiptCode) {
        EntryInfo entryInfo = entryInfoFacade.getEntryInfoByCode(receiptCode);

        statusFacade.getStatusByReceiptCode(entryInfo.getReceiptCode()).cancelIsSubmitted();
    }
}
