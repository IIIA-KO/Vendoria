package com.vendoria.user.controller;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.handlers.ErrorHandler;
import com.vendoria.security.entity.CustomUserDetails;
import com.vendoria.security.service.CookieService;
import com.vendoria.user.dto.UserDto;
import com.vendoria.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final CookieService cookieService;

    @GetMapping("/signin")
    public ModelAndView showSignInPage() {
        return new ModelAndView("signin");
    }

    @PostMapping("/signin")
    public ModelAndView handleSignIn(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        ResultWithValue<UserDto> result = userService.signIn(username, password);

        return ErrorHandler.handleResultWithValue(
                result,
                "redirect:/",
                "signin",
                "user"
        );
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
        Result result = userService.register(username, email, password);

        return ErrorHandler.handleResult(result, "redirect:/", "register");
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }

        cookieService.clearCookie(request, response);
        SecurityContextHolder.clearContext();

        return "redirect:/";
    }

    @GetMapping("/home")
    public ModelAndView home(@AuthenticationPrincipal CustomUserDetails user) {
        var modelAndView = new ModelAndView("home");
        modelAndView.addObject("user", user.getUsername());
        return modelAndView;
    }
}