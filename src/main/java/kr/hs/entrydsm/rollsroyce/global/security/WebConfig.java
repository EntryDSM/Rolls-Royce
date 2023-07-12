package kr.hs.entrydsm.rollsroyce.global.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.user-origin}") private String userOrigin;

    @Value("${cors.admin-origin}") private String adminOrigin;

    @Value("${cors.auth-origin}") private String authOrigin;
    @Value("${cors.landing-origin}") private String landingOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
                .allowedOrigins(
                        userOrigin,
                        adminOrigin,
                        "http://localhost:3000",
                        "http://localhost:3001",
                        "http://localhost:3002",
                        authOrigin,
                        landingOrigin);
    }
}
