package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeStudyPlanRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChangeStudyPlanService {

    private final UserFacade userFacade;

    public void execute(ChangeStudyPlanRequest request) {
        User user = userFacade.getUserByCode(
                userFacade.getCurrentReceiptCode()
        );
        user.updateStudyPlan(request.getContent());
    }
}
