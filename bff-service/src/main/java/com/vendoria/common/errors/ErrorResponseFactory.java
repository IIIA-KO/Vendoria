package com.vendoria.common.errors;

import com.vendoria.exception.response.ErrorResponse;

import java.sql.Timestamp;

public class ErrorResponseFactory {
    public static ErrorResponse create(Error error) {
        return ErrorResponse.builder()
                .status(getStatusCode(error))
                .type(error.getCode())
                .title(getErrorTitle(error))
                .error(error.getMessage())
                .details(getErrorDetails(error))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    public static ErrorResponse createUnexpected() {
        return ErrorResponse.builder()
                .status(500)
                .type("Error.Unexpected")
                .title("Unexpected error")
                .error("Unexpected error has occurred")
                .details("Try later")
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    private static int getStatusCode(Error error) {
        if (error instanceof NotFoundError) return 404;
        if (error instanceof UnauthorizedError) return 401;
        return 400;
    }

    private static String getErrorTitle(Error error) {
        if (error instanceof NotFoundError) return "Resource not found";
        if (error instanceof UnauthorizedError) return "Authorization Error";
        return "Bad request";
    }

    private static String getErrorDetails(Error error) {
        if (error instanceof NotFoundError) return "Requested resource does not exist or was deleted";
        if (error instanceof UnauthorizedError) return "Check that the data you entered is correct or try again later";
        return error.getMessage();
    }
}