package com.vendoria.bff.feign.client;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.user.dto.UserDto;
import com.vendoria.user.entity.User;
import com.vendoria.user.requests.RegisterUserRequest;
import com.vendoria.user.requests.SignInUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("VENDORIA-API")
public interface IVendoriaApiClient {
    @GetMapping("/api/users")
    User getUserByUsername(@RequestParam String username);

    @PostMapping("/api/auth/signin")
    ResultWithValue<UserDto> signIn(@RequestBody @Validated SignInUserRequest request);

    @PostMapping("/api/auth/register")
    Result register(@RequestBody @Validated RegisterUserRequest request);
}
