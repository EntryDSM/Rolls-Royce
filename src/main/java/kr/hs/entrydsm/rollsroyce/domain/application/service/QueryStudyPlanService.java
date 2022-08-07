package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryStudyPlanResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueryStudyPlanService {

    private final UserFacade userFacade;

    public QueryStudyPlanResponse execute() {
        return new QueryStudyPlanResponse(
                userFacade.queryStudyPlan()
        );
    }

}
