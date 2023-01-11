package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateIsPrintsArrivedService {

    private final StatusFacade statusFacade;
    private final EntryInfoFacade entryInfoFacade;

    public void execute(long receiptCode, boolean isArrived) {
        entryInfoFacade.getEntryInfoByCode(receiptCode); //todo 검증용
        Status status = statusFacade.getStatusByReceiptCode(receiptCode);

        status.updateIsPrintsArrived(isArrived);
        statusFacade.saveStatus(status);
    }

}
