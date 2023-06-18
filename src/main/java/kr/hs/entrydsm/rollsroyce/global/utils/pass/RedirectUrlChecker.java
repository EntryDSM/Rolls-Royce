package kr.hs.entrydsm.rollsroyce.global.utils.pass;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.hs.entrydsm.rollsroyce.domain.auth.exception.InvalidUrlException;

@Component
public class RedirectUrlChecker {
    @Value("${pass.base-url}") private String BASE_URL;

    public void checkRedirectUrl(String redirectUrl) {
        if (!redirectUrl.startsWith(BASE_URL)) {
            throw InvalidUrlException.EXCEPTION;
        }
    }
}
