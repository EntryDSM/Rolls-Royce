package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.response.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatusService {

    private final UserFacade userFacade;
    private final StatusFacade statusFacade;

    public StatusResponse execute() {
        final User user = userFacade.getCurrentUser();
        final Status status = statusFacade.getStatusByReceiptCode(user.getReceiptCode());

        return StatusResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getTelephoneNumber())
                .name(user.getName())
                .isSubmitted(status.getIsSubmitted())
                .isPrintedArrived(status.getIsPrintsArrived())
                .applicationType(user.getApplicationType())
                .selfIntroduce(user.getSelfIntroduce())
                .studyPlan(user.getStudyPlan())
                .build();
    }

}
