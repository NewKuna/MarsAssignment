package com.example.demo.common.filter;


import com.example.demo.common.Constants;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationFilter implements Filter {

    private static final String X_CORRELATION_ID = "X-CORRELATION-ID";
    private static final String CORRELATION_ID = "CORRELATION_ID";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Initiating CorrelationFilter filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String xCorrelationId = request.getHeader(X_CORRELATION_ID);
        xCorrelationId = StringUtils.isEmpty(xCorrelationId)
                ? request.getHeader(Constants.REQUEST_UID)
                : xCorrelationId;
        xCorrelationId = StringUtils.isEmpty(xCorrelationId)
                ? UUID.randomUUID().toString()
                : xCorrelationId;

        ThreadContext.put(CORRELATION_ID, xCorrelationId);
        ThreadContext.put(Constants.REQUEST_UID, xCorrelationId);

        filterChain.doFilter(request, response);
    }
}
