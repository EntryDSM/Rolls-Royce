package kr.hs.entrydsm.rollsroyce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class})
public class RollsRoyceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RollsRoyceApplication.class, args);
    }
}
