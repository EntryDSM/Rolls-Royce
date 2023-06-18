package kr.hs.entrydsm.rollsroyce.global.utils.pass;

import kr.hs.entrydsm.rollsroyce.domain.auth.exception.RedirectUrlNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class RedirectUrlGenerator {
    @Value("${pass.base-url}") private String BASE_URL;

    public String getRedirectUrl(String urlType) {
        return BASE_URL + parseUrlByUrlType(urlType);
    }

    private String parseUrlByUrlType(String urlType) {
        return Stream.of(UrlType.values())
                .filter(it -> it.getType().equals(urlType))
                .map(UrlType::getUrl)
                .findFirst()
                .orElseThrow(() -> RedirectUrlNotFoundException.EXCEPTION);
    }

    @Getter
    @AllArgsConstructor
    private enum UrlType {
        SIGN_UP("signUp", "/user"),
        CHANGE_PASSWORD("password","/user/password");

        private final String type;
        private final String url;
    }
}
