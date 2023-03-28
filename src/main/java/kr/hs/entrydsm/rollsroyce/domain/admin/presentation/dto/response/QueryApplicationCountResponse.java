package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;

@Getter
@AllArgsConstructor
public class QueryApplicationCountResponse {

    private final ApplicationType applicationType;

    private final Boolean isDaejeon;

    private final int count;
}
