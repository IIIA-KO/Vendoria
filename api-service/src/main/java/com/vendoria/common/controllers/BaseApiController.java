package com.vendoria.common.controllers;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import org.springframework.http.ResponseEntity;


public class BaseApiController {
    protected <T> ResponseEntity<?> handleResultWithValue(ResultWithValue<T> result) {
        if (result.isSuccess()) {
            return result.value() != null ? ResponseEntity.ok(result.value()) : ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(200).body(result);
    }

    protected ResponseEntity<?> handleResult(Result result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(200).body(result);
    }
}
