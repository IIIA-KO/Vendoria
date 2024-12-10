package com.vendoria.apiservice;

import com.vendoria.user.dto.UserDto;
import com.vendoria.user.entity.Role;
import com.vendoria.user.entity.User;
import com.vendoria.user.mapper.UserDtoMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserDtoMapperTest {
    private final UserDtoMapper userDtoMapper = new UserDtoMapper();

    @Test
    void testMapUserToUserDto() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setRole(Role.USER);

        UserDto userDto = userDtoMapper.mapUserToUserDto(user);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDto.getRole()).isEqualTo(user.getRole().name());
    }
}
