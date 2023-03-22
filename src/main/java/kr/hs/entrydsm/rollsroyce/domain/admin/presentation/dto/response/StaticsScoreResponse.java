package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.Getter;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;

@Getter
public abstract class StaticsScoreResponse {

    protected ApplicationType applicationType;

    protected boolean isDaejeon;

    public abstract void addScore(double score);
}
