package kr.hs.entrydsm.rollsroyce.domain.admin.facade;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.TokenResponse;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
@Component
public class AdminAuthFacade {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${auth.jwt.refreshExp}")
    private long ttl;

    public TokenResponse getToken(String id, String refreshToken) {
        String accessToken = jwtTokenProvider.generateAccessToken(id, "admin");
        return new TokenResponse(accessToken, refreshToken);
    }

}
