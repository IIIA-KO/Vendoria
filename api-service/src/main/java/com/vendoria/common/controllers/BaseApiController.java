package com.vendoria.common.controllers;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.errors.Error;
import com.vendoria.common.errors.NotFoundError;
import com.vendoria.common.errors.UnauthorizedError;
import org.springframework.http.ResponseEntity;


public class BaseApiController {
    protected <T> ResponseEntity<?> handleResultWithValue(ResultWithValue<T> result) {
        if (result.isSuccess()) {
            return result.value() != null ? ResponseEntity.ok(result.value()) : ResponseEntity.notFound().build();
        }
        return handleError(result.error());
    }

    protected ResponseEntity<?> handleResult(Result result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok().build();
        }
        return handleError(result.error());
    }

    private ResponseEntity<?> handleError(Error error) {
        if (error instanceof NotFoundError) {
            return ResponseEntity.status(404).body(error);
        } else if (error instanceof UnauthorizedError) {
            return ResponseEntity.status(401).body(error);
        }
        return ResponseEntity.badRequest().body(error);
    }
}
