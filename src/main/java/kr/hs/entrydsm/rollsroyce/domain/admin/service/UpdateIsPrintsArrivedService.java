package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;

@RequiredArgsConstructor
@Service
public class UpdateIsPrintsArrivedService {

    private final SESUtil sesUtil;

    private final StatusFacade statusFacade;

    private final UserFacade userFacade;

    public void execute(long receiptCode, boolean isArrived) {
        User user = userFacade.getUserByCode(receiptCode);
        Status status = statusFacade.getStatusByReceiptCode(receiptCode);

        status.updateIsPrintsArrived(isArrived);
        statusFacade.saveStatus(status);

        String template;
        if (!status.getIsPrintsArrived()) template = "PRINTED_NOT_ARRIVED";
        else template = "PRINTED_ARRIVED";

        Map<String, String> params = new HashMap<>();
        params.put("name", user.getName());

        sesUtil.sendMessage(user.getEmail(), template, params);
    }
}
