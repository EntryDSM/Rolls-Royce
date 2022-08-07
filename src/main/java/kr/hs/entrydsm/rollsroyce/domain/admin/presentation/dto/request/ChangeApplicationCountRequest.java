package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeApplicationCountRequest {

    @NotBlank
	private String applicationType;

    @NotNull
	private Boolean isDaejeon;

    @NotNull
	private Integer count;

}
