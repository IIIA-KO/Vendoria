package com.vendoria.common.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Objects;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Cloneable, Comparable<BaseEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BaseEntity other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (BaseEntity) super.clone();
    }

    @Override
    public int compareTo(BaseEntity o) {
        return this.id.compareTo(o.id);
    }
}
