package com.vendoria.user.service;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.user.entity.User;
import com.vendoria.user.errors.UserErrors;
import com.vendoria.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResultWithValue<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(Result::successWithValue)
                .orElse(
                        Result.failureWithResult(UserErrors.NOT_FOUND)
                );
    }

    public Result registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return Result.failure(UserErrors.ALREADY_EXISTS);
        }

        userRepository.save(user);
        return Result.success();
    }

    public ResultWithValue<User> loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return Result.failureWithResult(UserErrors.NOT_FOUND);
        }

        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            return Result.failureWithResult(UserErrors.INVALID_CREDENTIALS);
        }

        return Result.successWithValue(user);
    }
}