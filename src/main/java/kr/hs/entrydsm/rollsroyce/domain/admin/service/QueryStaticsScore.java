package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.CommonScoreResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.SpecialScoreResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.StaticsScoreResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.facade.ScoreFacade;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.types.ApplicationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QueryStaticsScore {

    private final ScoreFacade scoreFacade;

    public List<StaticsScoreResponse> execute() {
        List<StaticsScoreResponse> result = new ArrayList<>();

        for (ApplicationType type : ApplicationType.values()) {
            for (int i = 0; i < 2; i++) {
                StaticsScoreResponse response;
                if (type.isCommon())
                    response = new CommonScoreResponse(i != 0);
                else response = new SpecialScoreResponse(type, i != 0);
                scoreFacade.queryScoreByApplicationTypeAndIsDaejeon(type, i != 0)
                        .forEach(score ->
                                response.addScore(Math.round(score.getTotalScore().doubleValue()))
                        );
                result.add(response);
            }
        }

        return result;
    }

}
