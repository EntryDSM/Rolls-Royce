package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.AdminRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.TokenResponse;
import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.RefreshToken;
import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.repository.RefreshTokenRepository;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final AdminRepository adminRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Value("${auth.jwt.refreshExp}")
    private long ttl;

    private static final String ROLE = "admin";

    public TokenResponse execute(LoginRequest request) {
        return adminRepository.findById(request.getId())
                .filter(admin -> passwordEncoder.matches(request.getPassword(), admin.getPassword()))
                .map(Admin::getId)
                .map(adminId -> {
                    String refreshToken = jwtTokenProvider.generateRefreshToken(adminId, ROLE);
                    refreshTokenRepository.save(new RefreshToken(adminId, refreshToken, ttl));

                    String accessToken = jwtTokenProvider.generateAccessToken(adminId, ROLE);
                    return new TokenResponse(accessToken, refreshToken);
                })
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
    }

}
