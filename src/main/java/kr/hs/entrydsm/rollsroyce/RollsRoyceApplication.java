package kr.hs.entrydsm.rollsroyce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RollsRoyceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RollsRoyceApplication.class, args);
	}

}