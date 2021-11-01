package kr.hs.entrydsm.rollsroyce.global.utils.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private final String accessToken;

    private final String refreshToken;

}
