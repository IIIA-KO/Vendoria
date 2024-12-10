package com.vendoria.user.requests;

import com.vendoria.common.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterUserRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Password(minLength = 6, requireSpecialChar = true, requireNumber = true, requireUpperCase = true)
    private String password;

    @NotBlank
    @Size(min = 2, max = 50)
    private String username;
}
