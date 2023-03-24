package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import feign.Logger;
import feign.codec.ErrorDecoder;

@EnableFeignClients(basePackages = "kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis")
@Import(FeignClientErrorDecoder.class)
@Configuration
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    @ConditionalOnMissingBean(value = ErrorDecoder.class)
    public FeignClientErrorDecoder commonFeignErrorDecoder() {
        return new FeignClientErrorDecoder();
    }
}
