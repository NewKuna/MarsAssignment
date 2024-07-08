package com.example.demo.common.config;

import com.example.demo.common.filter.CorrelationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CorrelationFilter> filterRegistrationBean() {
        FilterRegistrationBean<CorrelationFilter> registrationBean = new FilterRegistrationBean<>();
        CorrelationFilter correlationFilter = new CorrelationFilter();
        registrationBean.setFilter(correlationFilter);
        registrationBean.addUrlPatterns("/**");
        registrationBean.setAsyncSupported(true);

        return registrationBean;
    }
}
