package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.entryInfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryInfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CancelApplicationSubmitService {

    private final StatusFacade statusFacade;

    private final EntryInfoFacade entryInfoFacade;

    private static final String TEMPLATE = "SUBMIT_FALSE";

    @Transactional
    public void execute(long receiptCode) {
        EntryInfo entryInfo = entryInfoFacade.getEntryInfoByCode(receiptCode);

        statusFacade.getStatusByReceiptCode(entryInfo.getReceiptCode())
                .cancelIsSubmitted();
    }

}
