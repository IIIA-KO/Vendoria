package com.vendoria.user.mapper;

import com.vendoria.user.entity.Role;
import com.vendoria.user.entity.User;
import com.vendoria.user.requests.RegisterUserRequest;

public class UserRequestMapper {
    public static User mapRegisterUserRequestToUser(RegisterUserRequest request) {
        return User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();

    }
}
