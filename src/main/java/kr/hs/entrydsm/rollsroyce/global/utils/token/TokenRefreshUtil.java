package kr.hs.entrydsm.rollsroyce.global.utils.token;

import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.repository.RefreshTokenRepository;
import kr.hs.entrydsm.rollsroyce.global.exception.InvalidTokenException;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;
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
        if(jwtTokenProvider.isNotRefreshToken(refreshToken)) {
            throw InvalidTokenException.EXCEPTION;
        }

        return refreshTokenRepository.findByToken(refreshToken)
                .filter(token -> jwtTokenProvider.getRole(token.getToken()).equals(role))
                .map(token -> {
                    String id = token.getId();
                    TokenResponse tokenResponse = jwtTokenProvider.generateToken(id, role);
                    token.update(tokenResponse.getRefreshToken(), ttl);
                    return new TokenResponse(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());
                })
                .orElseThrow(()-> InvalidTokenException.EXCEPTION);
    }

}
