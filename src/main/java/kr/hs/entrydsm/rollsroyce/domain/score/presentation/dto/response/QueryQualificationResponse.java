package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class QueryQualificationResponse {
    private final BigDecimal averageScore;

    public QueryQualificationResponse(QualificationCase qualificationExamCase) {
        this.averageScore = qualificationExamCase.getAverageScore();
    }

}
