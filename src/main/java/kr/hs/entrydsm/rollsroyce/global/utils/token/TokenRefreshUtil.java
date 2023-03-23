package kr.hs.entrydsm.rollsroyce.global.utils.token;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.hs.entrydsm.rollsroyce.domain.refreshtoken.domain.repository.RefreshTokenRepository;
import kr.hs.entrydsm.rollsroyce.global.exception.InvalidTokenException;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;

@RequiredArgsConstructor
@Component
public class TokenRefreshUtil {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${auth.jwt.refreshExp}") private long ttl;

    public TokenResponse tokenRefresh(String refreshToken) {
        if (jwtTokenProvider.isNotRefreshToken(refreshToken)) {
            throw InvalidTokenException.EXCEPTION;
        }

        return refreshTokenRepository
                .findByToken(refreshToken)
                .map(token -> {
                    String id = token.getId();
                    String role = jwtTokenProvider.getRole(token.getToken());

                    TokenResponse tokenResponse = jwtTokenProvider.generateToken(id, role);
                    token.update(tokenResponse.getRefreshToken(), ttl);
                    return new TokenResponse(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());
                })
                .orElseThrow(() -> InvalidTokenException.EXCEPTION);
    }
}
