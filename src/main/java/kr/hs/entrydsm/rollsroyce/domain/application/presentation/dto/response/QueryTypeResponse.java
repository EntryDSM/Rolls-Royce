package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QueryTypeResponse {

	private final String educationalStatus;
	private final String applicationType;
	private final boolean isDaejeon;
	private final String applicationRemark;
	private final String graduatedAt;
	private final boolean isGraduated;

}
