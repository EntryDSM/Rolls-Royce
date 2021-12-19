package kr.hs.entrydsm.rollsroyce.global.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
                .allowedOrigins("http://localhost:3000", "http://localhost:3001",
						"http://localhost:3002", "https://apply.entrydsm.hs.kr");
    }

}
