package com.vendoria.common.errors;

import lombok.Getter;

@Getter
public class Error {
    private final String code;
    private final String message;

    public static final Error NONE = new Error("", "");
    public static final Error NULL_VALUE = new Error("Error.NullValue", "Null value was provided");
    public static final Error UNEXPECTED = new Error("Error.Unexpected", "An unexpected error occurred");

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }
}