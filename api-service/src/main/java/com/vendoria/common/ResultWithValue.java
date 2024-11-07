package com.vendoria.common;

import com.vendoria.common.errors.Error;

public class ResultWithValue<T> extends Result {
    private final T value;

    public ResultWithValue(T value, boolean isSuccess, Error error) {
        super(isSuccess, error);
        this.value = value;
    }

    public T value() {
        if (isSuccess()) {
            return value;
        } else {
            throw new IllegalStateException("The value of a failure result cannot be accessed.");
        }
    }

    public static <T> ResultWithValue<T> fromValue(T value) {
        return Result.create(value);
    }
}
