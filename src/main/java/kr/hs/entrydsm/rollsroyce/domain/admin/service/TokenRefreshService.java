package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.global.security.dto.TokenResponse;
import kr.hs.entrydsm.rollsroyce.global.security.util.TokenRefreshUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TokenRefreshService {

    private final TokenRefreshUtil tokenRefreshUtil;

    @Transactional
    public TokenResponse execute(String existingRefreshToken) {
        return tokenRefreshUtil.tokenRefresh(existingRefreshToken, "admin");
    }

}
