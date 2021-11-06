package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.global.utils.token.TokenRefreshUtil;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTokenRefreshService {

    private final TokenRefreshUtil tokenRefreshUtil;

    @Transactional
    public TokenResponse execute(String refreshToken) {
        return tokenRefreshUtil.tokenRefresh(refreshToken, "user");
    }

}
