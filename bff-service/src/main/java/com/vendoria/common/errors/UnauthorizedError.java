package com.vendoria.common.errors;

public class UnauthorizedError extends Error {
    public UnauthorizedError(String code, String message) {
        super(code, message);
    }
}
