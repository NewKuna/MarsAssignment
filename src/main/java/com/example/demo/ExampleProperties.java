package com.example.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties("app.template")
@Configuration
public class ExampleProperties {
    private Integer requestTimeout;
    private Integer connectTimeout;
    private Integer socketTimeout;
    private Integer maxRequestConnection;
    private Integer maxRequestConnectionPerRoute;
}
