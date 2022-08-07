package kr.hs.entrydsm.rollsroyce.global.config;

import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtProperties;
import kr.hs.entrydsm.rollsroyce.global.security.logging.LogProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({JwtProperties.class, LogProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {
}
