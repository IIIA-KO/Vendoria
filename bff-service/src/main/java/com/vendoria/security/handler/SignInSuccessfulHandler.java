package com.vendoria.security.handler;

import com.google.gson.Gson;
import com.vendoria.security.entity.CustomUserDetails;
import com.vendoria.security.service.CookieService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class SignInSuccessfulHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${security.app.authentication.cookie}")
    private String USER_CREDENTIALS_COOKIE;

    private final CookieService cookieService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        String jsonPrincipal = new Gson().toJson(principal);
        cookieService.createCookie(USER_CREDENTIALS_COOKIE, jsonPrincipal, 24 * 60 * 60, response);
        response.sendRedirect("/");
    }
}
