package kr.hs.entrydsm.rollsroyce.domain.auth.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryPassInfoResponse {
    private final String phoneNumber;
    private final String name;
}
