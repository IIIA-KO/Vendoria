package com.vendoria.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface Password {
    String message() default  "Password must contain at least {minLength} characters, one uppercase letter, one number and one special character";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int minLength() default 8;
    boolean requireSpecialChar() default true;
    boolean requireNumber() default true;
    boolean requireUpperCase() default true;
}