package com.vendoria.user.controller;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.controllers.BaseApiController;
import com.vendoria.common.errors.Error;
import com.vendoria.user.dto.UserDto;
import com.vendoria.user.requests.RegisterUserRequest;
import com.vendoria.user.requests.SignInUserRequest;
import com.vendoria.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController extends BaseApiController {
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Validated SignInUserRequest request) {
        return this.handleResultWithValue(userService.signInUser(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody @Valid RegisterUserRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            return this.handleResult(Result.failure(
                    new Error("Validation.Error", errorMessage)
            ));
        }
        var result = this.userService.registerUser(request);
        return this.handleResult(result);
    }
}
