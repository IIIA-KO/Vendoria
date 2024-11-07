package com.vendoria.user.controller;

import com.vendoria.bff.feign.client.IVendoriaApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final IVendoriaApiClient vendoriaApiClient;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam String email,
            @RequestParam String password
    ) {
        vendoriaApiClient.register(email, password);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/home")
    public ResponseEntity<?> home(@AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }

        return ResponseEntity.ok("Welcome " + user.getUsername() + "!");
    }
}