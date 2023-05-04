package kr.hs.entrydsm.rollsroyce.domain.auth.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryPassInfoResponse {
    private final String phoneNumber;
}
