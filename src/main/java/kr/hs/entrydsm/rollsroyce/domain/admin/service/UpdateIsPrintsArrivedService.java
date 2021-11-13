package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import java.util.HashMap;
import java.util.Map;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateIsPrintsArrivedService {

    private final AdminFacade adminFacade;
    private final UserFacade userFacade;
    private final StatusFacade statusFacade;

    private final SESUtil sesUtil;

    @Transactional
    public void execute(long receiptCode) {
        User user = userFacade.getUserByCode(receiptCode);
        Status status = statusFacade.getStatusByReceiptCode(receiptCode);

        if (adminFacade.getAdminRole() == Role.ROLE_CONFIRM_FEE) {
            throw AdminNotAccessibleException.EXCEPTION;
        }
        String template;
        if (status.getIsPrintsArrived()) template = "PRINTED_NOT_ARRIVED";
        else template = "PRINTED_ARRIVED";

        status.updateIsPrintsArrived();

        Map<String, String> params = new HashMap<>();
        params.put("name", user.getName());

        sesUtil.sendMessage(user.getEmail(), template, params);
    }

}
