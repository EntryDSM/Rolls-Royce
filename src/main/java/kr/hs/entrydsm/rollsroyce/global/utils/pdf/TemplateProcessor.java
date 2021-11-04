package kr.hs.entrydsm.rollsroyce.global.utils.pdf;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemplateProcessor {

	private final TemplateEngine templateEngine;

	public String convertTemplateIntoHtmlString(String template, Map<String, Object> data) {
		Context context = new Context();
		context.setVariables(data);
		return templateEngine.process(template, context);
	}

}
