package kr.hs.entrydsm.rollsroyce.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

@EnableJpaAuditing
@Configuration
public class JpaAuditingConfiguration {

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
