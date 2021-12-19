package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeApplicationCountRequest {

	private String applicationType;
	private boolean isDaejeon;
	private int count;

}
