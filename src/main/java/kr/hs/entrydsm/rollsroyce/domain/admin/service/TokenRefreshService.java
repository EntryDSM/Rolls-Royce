package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;
import kr.hs.entrydsm.rollsroyce.global.utils.token.TokenRefreshUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TokenRefreshService {

    private final TokenRefreshUtil tokenRefreshUtil;

    @Transactional
    public TokenResponse execute(String refreshToken) {
        return tokenRefreshUtil.tokenRefresh(refreshToken);
    }

}
