package com.vendoria.user.controller;

import com.vendoria.common.controllers.BaseApiController;
import com.vendoria.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController extends BaseApiController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        return this.handleResultWithValue(userService.getUserByUsername(username));
    }
}
