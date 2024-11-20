package com.vendoria.bff.feign.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendoria.common.Result;
import com.vendoria.common.errors.ErrorResponseFactory;
import com.vendoria.exception.custom.ServiceException;
import com.vendoria.exception.response.ErrorResponse;
import feign.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import feign.Response;
import feign.codec.ErrorDecoder;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
@Log4j2
@RequiredArgsConstructor
public class CustomFeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String responseBody = null;
            if (response.body() != null) {
                responseBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
                log.debug("Error response from API: {}", responseBody);
            }

            log.debug("Response status: {}", response.status());

            if (responseBody != null) {
                Result result = objectMapper.readValue(responseBody, Result.class);
                return new FeignResultException(result.error());
            } else {
                return new ServiceException(
                        "No response body",
                        ErrorResponseFactory.createUnexpected()
                );
            }
        } catch (Exception e) {
            log.error("Error decoding response", e);
            return new ServiceException(
                    "Unexpected error",
                    ErrorResponseFactory.createUnexpected()
            );
        }
    }
}
