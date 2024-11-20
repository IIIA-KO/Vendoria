package com.vendoria.bff.feign.decoder;

import com.vendoria.common.errors.Error;

public class FeignResultException extends RuntimeException {
    private final com.vendoria.common.errors.Error error;
    
    public FeignResultException(com.vendoria.common.errors.Error error) {
        super(error.getMessage());
        this.error = error;
    }
    
    public Error getError() {
        return error;
    }
}
