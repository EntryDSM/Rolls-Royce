package kr.hs.entrydsm.rollsroyce.global.utils.ses;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "aws.ses")
public class AwsProperties {

    private final String accessKey;
    private final String secretKey;
    private final String region;

    public AwsProperties(String accessKey, String secretKey, String region) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

}
