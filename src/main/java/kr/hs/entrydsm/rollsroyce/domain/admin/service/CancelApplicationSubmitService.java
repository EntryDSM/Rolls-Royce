package kr.hs.entrydsm.rollsroyce.domain.admin.service;

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

    private final SESUtil sesUtil;

    private final StatusFacade statusFacade;

    private final UserFacade userFacade;

    private static final String TEMPLATE = "SUBMIT_FALSE";

    @Transactional
    public void execute(long receiptCode) {
        User user = userFacade.getUserByCode(receiptCode);

        statusFacade.getStatusByReceiptCode(user.getReceiptCode())
                .cancelIsSubmitted();

        Map<String, String> params = new HashMap<>();
        params.put("name", user.getName());
        sesUtil.sendMessage(user.getEmail(), TEMPLATE, params);
    }

}
