package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminAuthenticationFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CancelApplicationSubmitService {

    private final StatusRepository statusRepository;

    private final AdminAuthenticationFacade authenticationFacade;

    private final AdminFacade adminFacade;

    @Transactional
    public void execute(long receiptCode) {
        Status status = statusRepository.findById(receiptCode)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!adminFacade.getAdminRole(authenticationFacade.getEmail()).equals(Role.ROLE_CONFIRM_FEE)) {
            status.cancelIsSubmitted();
        } else {
            throw AdminNotAccessibleException.EXCEPTION;
        }
    }

}
