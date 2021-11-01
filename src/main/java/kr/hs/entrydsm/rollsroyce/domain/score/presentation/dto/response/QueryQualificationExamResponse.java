package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;

import java.math.BigDecimal;

public class QueryQualificationExamResponse {
    private final BigDecimal averageScore;

    public QueryQualificationExamResponse(QualificationCase qualificationExamCase) {
        this.averageScore = qualificationExamCase.getAverageScore();
    }
}
