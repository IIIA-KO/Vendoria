package com.vendoria.user.errors;

import com.vendoria.common.errors.NotFoundError;
import com.vendoria.common.errors.UnauthorizedError;

public class UserErrors {
    public static final NotFoundError NOT_FOUND
            = new NotFoundError("User.NotFound", "User with specified identifier was not found");

    public static final UnauthorizedError ALREADY_EXISTS
            = new UnauthorizedError("User.AlreadyExists", "User with specified identifier already exists");

    public static final UnauthorizedError INVALID_CREDENTIALS
            = new UnauthorizedError("User.InvalidCredentials", "Invalid credentials");
}
