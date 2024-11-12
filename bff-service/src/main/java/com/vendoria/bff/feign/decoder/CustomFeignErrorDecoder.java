package com.vendoria.bff.feign.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendoria.exception.custom.ServiceException;
import com.vendoria.exception.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Log4j2
@RequiredArgsConstructor
public class CustomFeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;
    private final ErrorDecoder defaultErrorDecoder = new Default();

    /*@Override
    public Exception decode(String methodKey, Response response) {
        try {
            String responseBody = null;
            if (response.body() != null) {
                responseBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            }

            if (responseBody == null || responseBody.isEmpty() || !isValidJson(responseBody)) {
                return defaultErrorDecoder.decode(methodKey, response);
            }

            ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
            return new ServiceException("API Error", errorResponse);
        } catch (IOException e) {
            log.error("Error decoding response: ", e);
            return new RuntimeException("Error processing response from server", e);
        }
    }

    private boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }*/

    @Override
    public Exception decode(String s, Response response) {
        ErrorResponse errorResponse;
        try {
            errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ServiceException(errorResponse.getDetails(), errorResponse);
    }
}
