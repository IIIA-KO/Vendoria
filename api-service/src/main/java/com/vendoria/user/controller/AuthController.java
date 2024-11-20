package com.vendoria.user.controller;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.controllers.BaseApiController;
import com.vendoria.user.dto.UserDto;
import com.vendoria.user.requests.RegisterUserRequest;
import com.vendoria.user.requests.SignInUserRequest;
import com.vendoria.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController extends BaseApiController {
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<ResultWithValue<UserDto>> signIn(@RequestBody @Validated SignInUserRequest request) {
        return this.handleResultWithValue(userService.signInUser(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Result> registerUser(@RequestBody @Validated RegisterUserRequest request) {
        var result = this.userService.registerUser(request);
        return this.handleResult(result);
    }
}
