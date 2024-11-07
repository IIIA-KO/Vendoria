package com.vendoria.common;

import com.vendoria.common.errors.Error;

public class Result {
    private final boolean isSuccess;
    private final Error error;

    public Result(boolean isSuccess, Error error) {
        if (isSuccess && !error.equals(Error.NONE)) {
            throw new IllegalArgumentException("Invalid operation");
        }
        if (!isSuccess && error.equals(Error.NONE)) {
            throw new IllegalArgumentException("Invalid operation");
        }
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isFailure() {
        return !isSuccess;
    }

    public Error error() {
        return error;
    }

    public static Result success() {
        return new Result(true, Error.NONE);
    }

    public static Result failure(Error error) {
        return new Result(false, error);
    }

    public static <T> ResultWithValue<T> successWithValue(T value) {
        return new ResultWithValue<>(value, true, Error.NONE);
    }

    public static <T> ResultWithValue<T> failureWithResult(Error error) {
        return new ResultWithValue<>(null, false, error);
    }

    public static <T> ResultWithValue<T> create(T value) {
        return (value != null) ? successWithValue(value) : failureWithResult(Error.NULL_VALUE);
    }
}