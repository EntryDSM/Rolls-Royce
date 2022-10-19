package kr.hs.entrydsm.rollsroyce.global.security.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.util.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestWrapper extends HttpServletRequestWrapper {

    private byte[] bytes;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            InputStream inputStream = request.getInputStream();
            this.bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
    }

    public String getBody() {
        String body = new String(bytes).replaceAll("\r\n", "");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (body.isEmpty() || body.isBlank())
                return "{}";

            Map bodyMap = objectMapper.readValue(body, Map.class);

            if (bodyMap == null)
                return "{}";

            if (bodyMap.get("password") != null || bodyMap.get("new_password") != null)
                bodyMap.put("password", "SECURED");

            body = objectMapper.writeValueAsString(bodyMap);
            return body;
        } catch (JsonProcessingException ignored) {
        }

        return "{}";
    }

    public String getParamsString() {
        StringBuilder params = new StringBuilder();
        getParameterMap().forEach((key, value) -> params.append(key).append("=").append(value[0]).append("&"));
        if (params.length() == 0) return "";
        params.deleteCharAt(params.lastIndexOf("&"));
        return params.insert(0, "?").toString();
    }

}
