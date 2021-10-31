package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.repository.RefreshTokenRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.response.TokenResponse;
import kr.hs.entrydsm.rollsroyce.global.exception.InvalidTokenException;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TokenRefreshService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${auth.jwt.refreshExp}")
    private long ttl;

    @Transactional
    public TokenResponse execute(String existingRefreshToken) {
        jwtTokenProvider.isRefreshToken(existingRefreshToken);

        return refreshTokenRepository.findByToken(existingRefreshToken)
                .filter(existingToken -> jwtTokenProvider.getRole(existingToken.getToken()).equals("user"))
                .map(existingToken -> {
                    String id = existingToken.getId();
                    String accessToken = jwtTokenProvider.generateAccessToken(id, "user");
                    String refreshToken = jwtTokenProvider.generateRefreshToken(id, "user");
                    existingToken.update(refreshToken, ttl);
                    return new TokenResponse(accessToken, refreshToken);
                })
                .orElseThrow(()-> InvalidTokenException.EXCEPTION);
    }

}
