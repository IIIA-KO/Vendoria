package com.vendoria.user.entity;

import com.vendoria.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Object clone() throws CloneNotSupportedException {
        User clone = (User) super.clone();
        clone.setPassword(null);
        return clone;
    }

    @Override
    public int compareTo(BaseEntity o) {
        if (!(o instanceof User other)) {
            return super.compareTo(o);
        }

        return username.compareTo(other.getUsername());
    }
}
