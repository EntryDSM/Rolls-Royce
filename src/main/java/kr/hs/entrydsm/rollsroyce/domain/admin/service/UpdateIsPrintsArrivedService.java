package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminAuthenticationFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateIsPrintsArrivedService {

    private final AdminAuthenticationFacade authenticationFacade;

    private final AdminFacade adminFacade;
    private final StatusFacade statusFacade;

    @Transactional
    public void execute(long receiptCode) {
        Status status = statusFacade.getStatusByReceiptCode(receiptCode);

        if (!(adminFacade.getAdminRole(authenticationFacade.getEmail()) == Role.ROLE_CONFIRM_FEE)) {
            status.updateIsPrintsArrived();
        } else {
            throw AdminNotAccessibleException.EXCEPTION;
        }
    }

}
