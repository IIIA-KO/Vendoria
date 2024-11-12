package com.vendoria.user.service;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.user.dto.UserDto;
import com.vendoria.user.entity.User;
import com.vendoria.user.errors.UserErrors;
import com.vendoria.user.mapper.UserDtoMapper;
import com.vendoria.user.mapper.UserRequestMapper;
import com.vendoria.user.persistence.UserRepository;
import com.vendoria.user.requests.RegisterUserRequest;
import com.vendoria.user.requests.SignInUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;

    public ResultWithValue<User> getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return Result.failureWithResult(UserErrors.NOT_FOUND);
        }

        User user = userOptional.get();
        return Result.successWithValue(user);
    }

    public Result registerUser(RegisterUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return Result.failure(UserErrors.ALREADY_EXISTS);
        }

        User user = UserRequestMapper.mapRegisterUserRequestToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return Result.success();
    }

    public ResultWithValue<UserDto> signInUser(SignInUserRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isEmpty()) {
            return Result.failureWithResult(UserErrors.NOT_FOUND);
        }

        User user = userOptional.get();
        if (!user.getPassword().equals(request.getPassword())) {
            return Result.failureWithResult(UserErrors.INVALID_CREDENTIALS);
        }

        return Result.successWithValue(userDtoMapper.mapUserToUserDto(user));
    }
}