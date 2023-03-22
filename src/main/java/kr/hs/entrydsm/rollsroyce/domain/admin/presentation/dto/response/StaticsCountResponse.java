package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;

@Getter
@AllArgsConstructor
public class StaticsCountResponse {

    private final ApplicationType applicationType;

    private final Boolean isDaejeon;

    private final int count;
}
