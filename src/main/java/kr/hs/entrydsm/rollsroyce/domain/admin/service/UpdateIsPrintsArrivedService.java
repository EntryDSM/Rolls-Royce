package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.entryInfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryInfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateIsPrintsArrivedService {

    private final SESUtil sesUtil;

    private final StatusFacade statusFacade;
    private final EntryInfoFacade entryInfoFacade;

    public void execute(long receiptCode, boolean isArrived) {
        EntryInfo entryInfo = entryInfoFacade.getEntryInfoByCode(receiptCode);
        Status status = statusFacade.getStatusByReceiptCode(receiptCode);

        status.updateIsPrintsArrived(isArrived);
        statusFacade.saveStatus(status);
    }

}
