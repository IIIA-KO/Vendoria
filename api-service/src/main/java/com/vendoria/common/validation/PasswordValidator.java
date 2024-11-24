package com.vendoria.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private int minLength;
    private boolean requireSpecialChar;
    private boolean requireNumber;
    private boolean requireUpperCase;

    @Override
    public void initialize(Password password) {
        this.minLength = password.minLength();
        this.requireSpecialChar = password.requireSpecialChar();
        this.requireNumber = password.requireNumber();
        this.requireUpperCase = password.requireUpperCase();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;
        
        if (password.length() < minLength) return false;
        
        if (requireNumber && !Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }
        
        if (requireUpperCase && !Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }

        return !requireSpecialChar || password.chars().anyMatch(ch -> SPECIAL_CHARS.indexOf(ch) >= 0);
    }
} 