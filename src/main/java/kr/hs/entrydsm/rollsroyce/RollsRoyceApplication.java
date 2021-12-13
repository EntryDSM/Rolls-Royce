package kr.hs.entrydsm.rollsroyce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {
		ThymeleafAutoConfiguration.class
})
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class RollsRoyceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RollsRoyceApplication.class, args);
	}

}
