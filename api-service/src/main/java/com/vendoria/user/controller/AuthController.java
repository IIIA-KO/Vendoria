package com.vendoria.user.controller;

import com.vendoria.common.Result;
import com.vendoria.common.controllers.BaseApiController;
import com.vendoria.user.entity.User;
import com.vendoria.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController extends BaseApiController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Result result = userService.registerUser(user);
        return this.handleResult(result);
    }
}
