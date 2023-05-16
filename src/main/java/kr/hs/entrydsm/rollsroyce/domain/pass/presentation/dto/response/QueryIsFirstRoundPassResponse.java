package kr.hs.entrydsm.rollsroyce.domain.pass.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryIsFirstRoundPassResponse {
    private final boolean isFirstRoundPass;
}
