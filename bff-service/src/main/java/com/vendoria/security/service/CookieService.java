package com.vendoria.security.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CookieService {

    public void createCookie(
            String cookieName,
            String data,
            int expirationDate,
            HttpServletResponse response
    ) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(cookieName);
        String encryptedData = encryptor.encrypt(data);
        Cookie cookie = new Cookie(cookieName, encryptedData);

        cookie.setHttpOnly(true);
        cookie.setMaxAge(expirationDate);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public String getCookie(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String data = null;

        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                String value = cookie.getValue();

                StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                encryptor.setPassword(cookieName);
                data = encryptor.decrypt(value);
            }
        }
        return data;
    }

    public void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(request.getCookies())
                .ifPresent(cookies ->
                        Arrays.stream(cookies)
                                .forEach(cookie -> {
                                    cookie.setMaxAge(0);
                                    cookie.setPath("/");
                                    response.addCookie(cookie);
                                })
                );
    }
}