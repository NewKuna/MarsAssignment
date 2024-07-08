package com.example.demo.common.model;

import com.example.demo.common.model.dto.StatusDto;
import lombok.Getter;

@Getter
public enum Status {
    SUCCESS("2000", 200, "Success"),
    CREATED("2001", 201, "Created"),
    INVALID_REQUEST("4001", 400, "Invalid request"),
    INVALID_HEADER("4002", 400,"Invalid headers"),
    MALFORMED_REQUEST("4003", 400, "Malformed request"),
    UNAUTHORIZED_REQUEST("4011", 401, "Unauthorized request"),
    INVALID_TOKEN("4012", 401, "Invalid Token"),
    EXPIRED_TOKEN("4013", 401, "Token has Expired"),
    FORBIDDEN_REQUEST("4031", 403, "Forbidden request"),
    RESOURCE_NOT_FOUND("4041", 404, "Data not found"),
    TIMED_OUT("4081", 408, "Timed out"),
    INTERNAL_SERVER_ERROR("5000", 500, "Internal Server Error"),
    INTERNAL_UNKNOWN_ERROR("5001", 500, "Unknown error"),
    EXTERNAL_UNKNOWN_ERROR("5002", 500, "Unknown error"),
    EXTERNAL_REQUEST_ERROR("5003", 500, "Can't perform a request to External server"),
    DATABASE_CONNECTION_ERROR("5004", 500, "Database connection error"),
    DATABASE_INTERNAL_ERROR("5005", 500, "Database internal error"),
    ELASTIC_SERVER_INTERNAL_ERROR("5006", 500, "Elastic internal error"),
    FILE_SERVER_INTERNAL_ERROR("5007", 500, "File server internal error"),
    ;

    private final String code;

    private final int httpStatus;
    private final String message;

    Status(String code, int httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public StatusDto getStatusDTO() {
        return StatusDto.builder()
                .code(this.code)
                .message(this.message)
                .build();
    }

}
