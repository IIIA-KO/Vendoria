package com.vendoria.apiservice;


import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.user.dto.UserDto;
import com.vendoria.user.entity.User;
import com.vendoria.user.errors.UserErrors;
import com.vendoria.user.mapper.UserDtoMapper;
import com.vendoria.user.persistence.UserRepository;
import com.vendoria.user.requests.RegisterUserRequest;
import com.vendoria.user.requests.SignInUserRequest;
import com.vendoria.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testSignInUser_Success() {
        // Arrange
        String username = "testUser";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        SignInUserRequest request = new SignInUserRequest(username, password);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(userDtoMapper.mapUserToUserDto(user)).thenReturn(new UserDto());

        // Act
        ResultWithValue<UserDto> result = userService.signInUser(request);

        // Assert
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.value()).isNotNull();
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testSignInUser_UserNotFound() {
        // Arrange
        String username = "nonExistentUser";
        String password = "password";
        SignInUserRequest request = new SignInUserRequest(username, password);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        ResultWithValue<UserDto> result = userService.signInUser(request);

        // Assert
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getError()).isEqualTo(UserErrors.NOT_FOUND);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testSignInUser_InvalidCredentials() {
        // Arrange
        String username = "testUser";
        String password = "wrongPassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("correctPassword"));

        SignInUserRequest request = new SignInUserRequest(username, password);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        // Act
        ResultWithValue<UserDto> result = userService.signInUser(request);

        // Assert
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getError()).isEqualTo(UserErrors.INVALID_CREDENTIALS);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        RegisterUserRequest request = new RegisterUserRequest("newUser", "new@example.com", "password");
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        when(userDtoMapper.mapUserToUserDto(user)).thenReturn(new UserDto());

        // Act
        Result result = userService.registerUser(request);

        // Assert
        assertThat(result.isSuccess()).isTrue();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_UsernameExists() {
        // Arrange
        RegisterUserRequest request = new RegisterUserRequest("existingUser", "new@example.com", "password");
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User()));

        // Act
        Result result = userService.registerUser(request);

        // Assert
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getError()).isEqualTo(UserErrors.ALREADY_EXISTS);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testGetUserByUsername_Success() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        ResultWithValue<User> result = userService.getUserByUsername(username);

        // Assert
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.value()).isNotNull();
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testGetUserByUsername_NotFound() {
        // Arrange
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        ResultWithValue<User> result = userService.getUserByUsername(username);

        // Assert
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getError()).isEqualTo(UserErrors.NOT_FOUND);
        verify(userRepository, times(1)).findByUsername(username);
    }
}
