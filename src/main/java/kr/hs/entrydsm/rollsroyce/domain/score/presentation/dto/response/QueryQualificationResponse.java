package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response;

import lombok.Getter;

import java.math.BigDecimal;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;

@Getter
public class QueryQualificationResponse {
    private final BigDecimal averageScore;

    public QueryQualificationResponse(QualificationCase qualificationExamCase) {
        this.averageScore = qualificationExamCase.getAverageScore();
    }
}
