package com.vendoria.user.service;

import com.vendoria.bff.feign.client.IVendoriaApiClient;
import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.errors.Error;
import com.vendoria.security.service.CookieService;
import com.vendoria.user.dto.UserDto;
import com.vendoria.user.entity.User;
import com.vendoria.user.requests.RegisterUserRequest;
import com.vendoria.user.requests.SignInUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IVendoriaApiClient vendoriaApiClient;
    private final CookieService cookieService;

    public User getUserByUsername(String username) {
        return vendoriaApiClient.getUserByUsername(username);
    }

    public ResultWithValue<UserDto> signIn(String username, String password) {
        var request = SignInUserRequest
                .builder()
                .username(username)
                .password(password)
                .build();

        return vendoriaApiClient.signIn(request);
    }

    public Result register(String username, String email, String password) {
        try {
            var request = RegisterUserRequest
                    .builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .build();

            Result result = vendoriaApiClient.register(request);
            return result;

        } catch (Exception e) {
            String errorMessage = e.toString();
            System.out.println(errorMessage);
            return Result.failure(Error.NONE);
        }
    }
}
