package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
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
        return StatusResponse.builder()
                .email(userFacade.getCurrentEmail())
                .phoneNumber(userFacade.getCurrentPhoneNumber())
                .name(userFacade.getCurrentName())
                .isSubmitted(statusFacade.getCurrentIsSubmitted())
                .isPrintedArrived(statusFacade.getCurrentIsPrintedArrived())
                .applicationType(userFacade.getCurrentApplicationType())
                .selfIntroduce(userFacade.getCurrentSelfIntroduce())
                .studyPlan(userFacade.getCurrentStudyPlan())
                .build();
    }

}
