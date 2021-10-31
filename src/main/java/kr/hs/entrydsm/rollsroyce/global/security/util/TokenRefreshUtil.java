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

    public TokenResponse tokenRefresh(String existingRefreshToken, String role) {
        jwtTokenProvider.isRefreshToken(existingRefreshToken);

        return refreshTokenRepository.findByToken(existingRefreshToken)
                .filter(existingToken -> jwtTokenProvider.getRole(existingToken.getToken()).equals(role))
                .map(existingToken -> {
                    String id = existingToken.getId();
                    String accessToken = jwtTokenProvider.generateAccessToken(id, role);
                    String refreshToken = jwtTokenProvider.generateRefreshToken(id, role);
                    existingToken.update(refreshToken, ttl);
                    return new TokenResponse(accessToken, refreshToken);
                })
                .orElseThrow(()-> InvalidTokenException.EXCEPTION);
    }

}
