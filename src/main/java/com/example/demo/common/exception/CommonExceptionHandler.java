package com.example.demo.common.exception;

import com.example.demo.common.model.dto.ResponseDto;
import com.example.demo.common.model.dto.StatusDto;
import com.example.demo.common.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ResponseDto<?>> handleClientErrorException(CommonException e) {
        log.error("Catching CommonException - Trace :: {}", ExceptionUtils.getStackTrace(e));
        Status status = e.getStatus();
        String description = StringUtils.isEmpty(e.getMessage()) ? null : e.getMessage();
        return createResponse(status, description);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ResponseDto<?>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.error("Catching MissingRequestHeaderException - Trace :: {}", ExceptionUtils.getStackTrace(e));
        String description = StringUtils.isEmpty(e.getMessage()) ? null : e.getMessage();
        return createResponse(Status.INVALID_HEADER, description);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity<ResponseDto<?>> handleMissingServletRequestParameterException(Exception e) {
        log.error("Catching MissingServletRequestParameterException - Trace :: {}", ExceptionUtils.getStackTrace(e));
        String description = StringUtils.isEmpty(e.getMessage()) ? null : e.getMessage();
        return createResponse(Status.INVALID_REQUEST, description);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ResponseDto<?>> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.error("Catching EmptyResultDataAccessException - Trace :: {}", ExceptionUtils.getStackTrace(e));
        return createResponse(Status.RESOURCE_NOT_FOUND, null);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseDto<?>> handlePSQLException(DataAccessException e) {
        log.error("Catching DataAccessException - Trace :: {}", ExceptionUtils.getStackTrace(e));
        return createResponse(Status.DATABASE_INTERNAL_ERROR, null);
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ResponseDto<?>> handleConnectionException(CannotCreateTransactionException e) {
        log.error("Catching ConnectionException - Trace :: {}", ExceptionUtils.getStackTrace(e));
        if (e.contains(ConnectException.class)) {
            return createResponse(Status.DATABASE_CONNECTION_ERROR, null);
        } else {
            return createResponse(Status.INTERNAL_SERVER_ERROR, null);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleExceptions(Exception e) {
        log.error("Catching Exception - Trace :: {}", ExceptionUtils.getStackTrace(e));
        return createResponse(Status.INTERNAL_SERVER_ERROR, null);
    }

    private ResponseEntity<ResponseDto<?>> createResponse(Status status, String description) {
        String code = status != null ? status.getCode() : "5000";
        int httpStatus = status != null ? status.getHttpStatus() : 500;
        String message = status != null ? status.getMessage() : "Internal Server Error";

        return ResponseEntity.status(httpStatus).body(
                ResponseDto.builder()
                        .status(StatusDto.builder().code(code).message(message).description(description).build())
                        .build()
        );
    }

}
