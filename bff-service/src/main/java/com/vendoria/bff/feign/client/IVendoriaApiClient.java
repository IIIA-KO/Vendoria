package com.vendoria.bff.feign.client;

import com.vendoria.bff.feign.config.FeignConfig;
import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.user.dto.UserDto;
import com.vendoria.user.entity.User;
import com.vendoria.user.requests.RegisterUserRequest;
import com.vendoria.user.requests.SignInUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "VENDORIA-API",
        configuration = FeignConfig.class
)
public interface IVendoriaApiClient {
    @GetMapping("/api/users")
    User getUserByUsername(@RequestParam String username);

    @PostMapping("/api/auth/signin")
    ResultWithValue<UserDto> signIn(@RequestBody @Validated SignInUserRequest request);

    @PostMapping("/api/auth/register")
    @ResponseStatus(HttpStatus.OK)
    Result register(@RequestBody @Validated RegisterUserRequest request);
}
