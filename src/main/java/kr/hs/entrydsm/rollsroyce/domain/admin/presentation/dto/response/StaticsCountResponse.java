package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.entryInfo.domain.types.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StaticsCountResponse {

	private final ApplicationType applicationType;

	private final Boolean isDaejeon;

	private final int count;

}
