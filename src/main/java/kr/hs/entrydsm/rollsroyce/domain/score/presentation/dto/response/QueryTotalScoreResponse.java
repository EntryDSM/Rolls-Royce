package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class QueryTotalScoreResponse {
    private final BigDecimal totalScore;

    public QueryTotalScoreResponse(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }
}
