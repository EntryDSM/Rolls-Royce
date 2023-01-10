package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

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
