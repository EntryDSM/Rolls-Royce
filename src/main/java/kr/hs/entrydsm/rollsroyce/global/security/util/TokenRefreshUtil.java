package kr.hs.entrydsm.rollsroyce.global.security.util;

import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.repository.RefreshTokenRepository;
import kr.hs.entrydsm.rollsroyce.global.exception.InvalidTokenException;
import kr.hs.entrydsm.rollsroyce.global.security.util.dto.TokenResponse;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenRefreshUtil {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${auth.jwt.refreshExp}")
    private long ttl;

    public TokenResponse tokenRefresh(String refreshToken, String role) {
        jwtTokenProvider.isRefreshToken(refreshToken);

        return refreshTokenRepository.findByToken(refreshToken)
                .filter(token -> jwtTokenProvider.getRole(token.getToken()).equals(role))
                .map(token -> {
                    String id = token.getId();
                    String accessToken = jwtTokenProvider.generateAccessToken(id, role);
                    String newRefreshToken = jwtTokenProvider.generateRefreshToken(id, role);
                    token.update(newRefreshToken, ttl);
                    return new TokenResponse(accessToken, newRefreshToken);
                })
                .orElseThrow(()-> InvalidTokenException.EXCEPTION);
    }

}
