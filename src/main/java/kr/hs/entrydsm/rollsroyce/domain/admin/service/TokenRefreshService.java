package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.global.utils.token.TokenRefreshUtil;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;

@RequiredArgsConstructor
@Service
public class TokenRefreshService {

    private final TokenRefreshUtil tokenRefreshUtil;

    @Transactional
    public TokenResponse execute(String refreshToken) {
        return tokenRefreshUtil.tokenRefresh(refreshToken);
    }
}
