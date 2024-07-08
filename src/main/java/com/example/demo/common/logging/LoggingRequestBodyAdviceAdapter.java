package com.example.demo.common.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.common.model.RequireHeaders;
import com.example.demo.common.util.WebUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class LoggingRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    private final HttpServletRequest httpServletRequest;
    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        if (!WebUtils.isActuatorEndpoints(httpServletRequest)) {
            logRequest(httpServletRequest, body);
        }

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    public void logRequest(HttpServletRequest request, Object body) {
        StringBuilder builder = new StringBuilder("Incoming request: curl -X ");

        Map<String, ?> parameters = buildParametersMap(request);
        if (parameters.isEmpty()) {
            builder.append(request.getMethod()).append(" '").append(request.getRequestURL()).append("' ");
        } else {
            builder.append(request.getMethod()).append(" '").append(request.getRequestURL());
            builder.append(parameters.keySet().stream()
                    .map(key -> key + "=" + parameters.get(key))
                    .collect(Collectors.joining("&", "?", "")));
            builder.append("' ");
        }

        Map<String, ?> headers = buildHeadersMap(request);
        headers.forEach((key, value) -> {
            builder.append("-H '").append(key).append(": ").append(value).append("' ");
        });

        if (body != null) {
            try {
                builder.append("-d '").append(objectMapper.writeValueAsString(body)).append("'");
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        }

        log.info(builder.toString(), StandardCharsets.UTF_8);
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        return Stream.of(RequireHeaders.values())
                .filter(header -> null != request.getHeader(header.getValue()))
                .collect(Collectors.toMap(RequireHeaders::getValue, header -> request.getHeader(header.getValue())));
    }

    private Map<String, String> buildParametersMap(HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }
}
