package kr.hs.entrydsm.rollsroyce.domain.admin.facade;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.TokenResponse;
import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.RefreshToken;
import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.repository.RefreshTokenRepository;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminAuthFacade {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${auth.jwt.refreshExp}")
    private long ttl;

    private static final String ROLE = "admin";

    public TokenResponse getToken(String id, String refreshToken) {
        refreshTokenRepository.save(new RefreshToken(id, refreshToken, ttl));

        String accessToken = jwtTokenProvider.generateAccessToken(id, ROLE);
        return new TokenResponse(accessToken, refreshToken);
    }

}
