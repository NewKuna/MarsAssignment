package com.example.demo.common.exception;

import com.example.demo.common.model.Status;

public class CommonException extends Exception {

    private Status status;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(Status status) {
        this.status = status;
    }

    public CommonException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public CommonException(Status status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Status status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

}
