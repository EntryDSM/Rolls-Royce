package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import java.util.HashMap;
import java.util.Map;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.SESUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CancelApplicationSubmitService {

    private final AdminFacade adminFacade;
    private final UserFacade userFacade;
    private final StatusFacade statusFacade;

    private final SESUtil sesUtil;

    private static final String TEMPLATE = "SUBMIT_FALSE";

    @Transactional
    public void execute(long receiptCode) {
        User user = userFacade.getUserByCode(receiptCode);

        if (adminFacade.getAdminRole() == Role.ROLE_CONFIRM_FEE) {
            throw AdminNotAccessibleException.EXCEPTION;
        }

        statusFacade.getStatusByReceiptCode(user.getReceiptCode())
				.cancelIsSubmitted();

        Map<String, String> params = new HashMap<>();
        params.put("name", user.getName());
        sesUtil.sendMessage(user.getEmail(), TEMPLATE, params);
    }

}
