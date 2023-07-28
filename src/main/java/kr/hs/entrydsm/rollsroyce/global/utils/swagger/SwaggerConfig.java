package kr.hs.entrydsm.rollsroyce.global.utils.swagger;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info =
                @Info(
                        title = "EntryDSM : Rolls-Royce API 명세서",
                        description = "입학 전형 시스템",
                        contact = @Contact(name = "EntryDSM", email = "entrydsm@entrydsm.hs.kr")))
@Configuration
public class SwaggerConfig {}
