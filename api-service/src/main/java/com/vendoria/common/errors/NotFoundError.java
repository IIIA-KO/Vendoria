package com.vendoria.common.errors;

public class NotFoundError extends Error {
    public NotFoundError(String code, String message) {
        super(code, message);
    }
}
