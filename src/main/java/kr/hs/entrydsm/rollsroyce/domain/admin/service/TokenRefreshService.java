package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.TokenResponse;
import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.repository.RefreshTokenRepository;
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

        return refreshTokenRepository.findById(existingRefreshToken)
                .filter(existingToken -> jwtTokenProvider.getRole(existingToken.getToken()).equals("admin"))
                .map(existingToken -> {
                    String id = existingToken.getId();
                    String accessToken = jwtTokenProvider.generateAccessToken(id, "admin");
                    String refreshToken = jwtTokenProvider.generateRefreshToken(id, "admin");
                    existingToken.update(refreshToken, ttl);
                    return new TokenResponse(accessToken, refreshToken);
                })
                .orElseThrow(()-> InvalidTokenException.EXCEPTION);
    }

}
