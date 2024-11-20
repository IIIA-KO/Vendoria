package com.vendoria.common.controllers;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.errors.Error;
import com.vendoria.common.errors.NotFoundError;
import com.vendoria.common.errors.UnauthorizedError;
import org.springframework.http.ResponseEntity;


public class BaseApiController {
    protected <T> ResponseEntity<ResultWithValue<T>> handleResultWithValue(ResultWithValue<T> result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }

        //return handleErrorWithValue(result.error());
        return ResponseEntity.status(200).body(ResultWithValue.failureWithResult(result.error()));
    }

    protected ResponseEntity<Result> handleResult(Result result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(200).body(Result.failure(result.error()));
        //return handleError(result.error());
    }

    private ResponseEntity<Result> handleError(Error error) {
        if (error instanceof NotFoundError) {
            return ResponseEntity.status(404).body(Result.failure(error));
        } else if (error instanceof UnauthorizedError) {
            return ResponseEntity.status(401).body(Result.failure(error));
        }

        return ResponseEntity.status(500).body(Result.failure(error));
    }

    private <T> ResponseEntity<ResultWithValue<T>> handleErrorWithValue(Error error) {
        if (error instanceof NotFoundError) {
            return ResponseEntity.status(404).body(ResultWithValue.failureWithResult(error));
        } else if (error instanceof UnauthorizedError) {
            return ResponseEntity.status(401).body(ResultWithValue.failureWithResult(error));
        }

        return ResponseEntity.status(500).body(ResultWithValue.failureWithResult(error));
    }
}
