package com.vendoria.user.mapper;

import com.vendoria.user.dto.UserDto;
import com.vendoria.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public UserDto mapUserToUserDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
