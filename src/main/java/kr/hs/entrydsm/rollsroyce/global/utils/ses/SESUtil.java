package kr.hs.entrydsm.rollsroyce.global.utils.ses;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Future;

@Log4j2
@Component
@RequiredArgsConstructor
public class SESUtil {

    private static final String UTF_8_ENCODED_SOURCE_NAME = "=?utf-8?B?7J6F7ZWZ7KCE7ZiV7Iuc7Iqk7YWc?=";

    private final ObjectMapper objectMapper;
    private final AmazonSimpleEmailServiceAsync amazonSimpleEmailServiceAsync;

    public boolean sendMessage(String email, String templateName, Map<String, String> params) {
        SendTemplatedEmailRequest request = new SendTemplatedEmailRequest()
                .withDestination(new Destination().withToAddresses(email))
                .withTemplate(templateName)
                .withSource(UTF_8_ENCODED_SOURCE_NAME + " <noreply@entrydsm.hs.kr>")
                .withTemplateData(paramToJson(params));

        try {
            Future<SendTemplatedEmailResult> result = amazonSimpleEmailServiceAsync.sendTemplatedEmailAsync(request);
            log.info(result);
            return result.isDone();
        } catch (MessageRejectedException e) {
            throw kr.hs.entrydsm.rollsroyce.global.exception.MessageRejectedException.EXCEPTION;
        }

    }

    @SneakyThrows
    private String paramToJson(Map<String, String> params) {
        String data = objectMapper.writeValueAsString(params);
        data = data.replaceAll("\"", "\\\"");
        return data;
    }

}
