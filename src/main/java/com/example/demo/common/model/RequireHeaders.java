package com.example.demo.common.model;

import lombok.Getter;

@Getter
public enum RequireHeaders {
    REQUEST_UID("request-uid"),
    REQUEST_APP_ID("request-app-id"),
    REQUEST_DATETIME("request-datetime"),
    SERVICE_NAME("service-name"),
    CONTENT_TYPE("content-type"),
    ACCEPT("accept"),
    USER_ID("user-id");

    private final String value;

    RequireHeaders(String value) {
        this.value = value;
    }

}
