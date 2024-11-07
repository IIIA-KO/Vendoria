package com.vendoria.exception.custom;

import com.vendoria.exception.response.ErrorResponse;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final String type = "Internal Microservice Exception";
    private final ErrorResponse errorResponse;

    public ServiceException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }
}
