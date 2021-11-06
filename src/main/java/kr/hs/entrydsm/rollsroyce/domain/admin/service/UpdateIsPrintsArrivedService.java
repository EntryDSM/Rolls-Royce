package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminAuthenticationFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
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
public class UpdateIsPrintsArrivedService {

    private final AdminAuthenticationFacade authenticationFacade;

    private final AdminFacade adminFacade;
    private final UserFacade userFacade;

    private final SESUtil sesUtil;

    @Transactional
    public void execute(long receiptCode) {
        User user = userFacade.getUserByCode(receiptCode);
        Status status = user.getStatus();

        if (adminFacade.getAdminRole(authenticationFacade.getEmail()) == Role.ROLE_CONFIRM_FEE) {
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
