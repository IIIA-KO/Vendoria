package com.vendoria.user.service;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.errors.Error;
import com.vendoria.user.dto.UserDto;
import com.vendoria.user.entity.Role;
import com.vendoria.user.entity.User;
import com.vendoria.user.errors.UserErrors;
import com.vendoria.user.mapper.UserDtoMapper;
import com.vendoria.user.persistence.UserRepository;
import com.vendoria.user.requests.RegisterUserRequest;
import com.vendoria.user.requests.SignInUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;

    public ResultWithValue<UserDto> signInUser(SignInUserRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUsername())
                    .orElse(null);

            if (user == null) {
                return Result.failureWithResult(UserErrors.NOT_FOUND);
            }

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return Result.failureWithResult(UserErrors.INVALID_CREDENTIALS);
            }

            UserDto userDto = userDtoMapper.mapUserToUserDto(user);
            return Result.successWithValue(userDto);
        } catch (Exception e) {
            log.error("Error during Sign In", e);
            return Result.failureWithResult(Error.UNEXPECTED);
        }
    }

    public Result registerUser(RegisterUserRequest request) {
        try {
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                Result result = Result.failure(UserErrors.ALREADY_EXISTS);

                return result;
            }

            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return Result.failureWithResult(UserErrors.EMAIL_EXISTS);
            }

            User user = User
                    .builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();

            userRepository.save(user);
            return Result.success();
        } catch (Exception e) {
            log.error("Error during Register", e);
            return Result.failure(Error.UNEXPECTED);
        }
    }

    public ResultWithValue<User> getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return Result.failureWithResult(UserErrors.NOT_FOUND);
        }

        User user = userOptional.get();
        return ResultWithValue.successWithValue(user);
    }
}