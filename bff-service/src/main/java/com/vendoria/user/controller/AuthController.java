package com.vendoria.user.controller;

import com.vendoria.security.entity.CustomUserDetails;
import com.vendoria.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/signin")
    public ModelAndView showSignInPage() {
        return new ModelAndView("signin");
    }

    @PostMapping("/signin")
    public ModelAndView handleSignIn(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        var result = userService.signIn(username, password);
        if (result.isFailure()) {
            ModelAndView modelAndView = new ModelAndView("signin");
            modelAndView.addObject("error", result.getError().getMessage());
            return modelAndView;
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView handleRegister(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model
    ) {
        var result = userService.register(username, email, password);
        if (result.isFailure()) {
            model.addAttribute("error", result.getError());
            return new ModelAndView("register");
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/home")
    public ModelAndView home(@AuthenticationPrincipal CustomUserDetails user) {
        var modelAndView = new ModelAndView("home");
        modelAndView.addObject("user", user.getUsername());
        return modelAndView;
    }
}