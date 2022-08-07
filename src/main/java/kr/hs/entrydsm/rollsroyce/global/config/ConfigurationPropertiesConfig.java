package kr.hs.entrydsm.rollsroyce.global.config;

import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtProperties;
import kr.hs.entrydsm.rollsroyce.global.security.logging.LogProperties;
import kr.hs.entrydsm.rollsroyce.global.utils.ses.AwsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({JwtProperties.class, LogProperties.class, AwsProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {
}
