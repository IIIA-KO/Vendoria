package com.vendoria.apiservice;

import com.vendoria.user.entity.User;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testUser");

        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("testUser");

        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    void testToString() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        String expectedString = "User(id=1, username=testUser, ...)";
        assertThat(user.toString()).contains("testUser");
    }
}
