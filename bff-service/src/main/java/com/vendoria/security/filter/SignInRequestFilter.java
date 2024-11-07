package com.vendoria.security.filter;

import com.google.gson.Gson;
import com.vendoria.security.entity.CustomUserDetails;
import com.vendoria.security.service.CookieService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class SignInRequestFilter extends OncePerRequestFilter {
    @Value("${security.app.authentication.cookie}")
    private String USER_CREDENTIALS_COOKIE;

    private final CookieService cookieService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jsonPrincipal = cookieService.getCookie(USER_CREDENTIALS_COOKIE, request);

        if (jsonPrincipal != null) {
            CustomUserDetails customUserDetails = new Gson().fromJson(jsonPrincipal, CustomUserDetails.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails,
                    null,
                    customUserDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else if (isProtectedUrl(request)) {
            response.sendRedirect("/signin");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isProtectedUrl(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.equals("/signin") && !path.equals("/register") && !path.equals("/home");
    }
}
