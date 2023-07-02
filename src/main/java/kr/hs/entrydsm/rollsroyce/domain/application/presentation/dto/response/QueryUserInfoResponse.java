package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryUserInfoResponse {
    private final String name;
    private final String telephoneNumber;
}
