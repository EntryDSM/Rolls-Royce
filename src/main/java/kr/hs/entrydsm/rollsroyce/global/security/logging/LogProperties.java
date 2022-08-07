package kr.hs.entrydsm.rollsroyce.global.security.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "rolls-royce.logging")
public class LogProperties {

    private String name;

    private String path;

    private int size;

}
