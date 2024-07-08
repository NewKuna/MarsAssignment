package com.example.demo.common.logging;

import com.example.demo.common.util.WebUtils;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@AllArgsConstructor
public class LoggingIncomingInterceptor implements HandlerInterceptor {

    private final LoggingRequestBodyAdviceAdapter loggingRequestBodyAdviceAdapter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                && (HttpMethod.GET.name().equals(request.getMethod()) || HttpMethod.DELETE.name().equals(request.getMethod()))
                && (!WebUtils.isActuatorEndpoints(request))) {

//        if (!hasRequestBody(request)) {

            loggingRequestBodyAdviceAdapter.logRequest(request, null);

        }

        return true;
    }

    private boolean hasRequestBody(HttpServletRequest request) throws IOException {
        return request.getContentLength() > 0 || request.getReader().ready();
    }

}
