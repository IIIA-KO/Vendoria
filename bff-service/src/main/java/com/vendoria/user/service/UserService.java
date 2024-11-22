package com.vendoria.user.service;

import com.vendoria.bff.feign.client.IVendoriaApiClient;
import com.vendoria.bff.feign.decoder.FeignResultException;
import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.errors.Error;
import com.vendoria.security.service.CookieService;
import com.vendoria.user.dto.UserDto;
import com.vendoria.user.entity.User;
import com.vendoria.user.requests.RegisterUserRequest;
import com.vendoria.user.requests.SignInUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final IVendoriaApiClient vendoriaApiClient;
    private final CookieService cookieService;

    public User getUserByUsername(String username) {
        var user = vendoriaApiClient.getUserByUsername(username);
        return user;
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

            var result = vendoriaApiClient.register(request);
            return result;
        } catch (FeignResultException e) {
            log.error("API error during registration: {}", e.getMessage());
            return Result.failure(e.getError());
        } catch (Exception e) {
            String errorMessage = e.toString();
            log.error("Unexpected error during registration", e);
            return Result.failure(Error.UNEXPECTED);
        }
    }
}
