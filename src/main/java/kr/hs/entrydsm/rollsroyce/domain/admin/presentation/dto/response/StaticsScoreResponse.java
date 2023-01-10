package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.types.ApplicationType;
import lombok.Getter;

@Getter
public abstract class StaticsScoreResponse {

	protected ApplicationType applicationType;

	protected boolean isDaejeon;

	public abstract void addScore(double score);

}
