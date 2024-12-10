package com.vendoria.apiservice;

import com.vendoria.common.entities.BaseEntity;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseEntityTest {
    @Test
    void testEqualsAndHashCode() {
        BaseEntity entity1 = new BaseEntity() {};
        entity1.setId(1L);

        BaseEntity entity2 = new BaseEntity() {};
        entity2.setId(1L);

        assertThat(entity1).isEqualTo(entity2);
        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        BaseEntity entity = new BaseEntity() {};
        entity.setId(1L);
        BaseEntity clonedEntity = (BaseEntity) entity.clone();

        assertThat(clonedEntity).isNotSameAs(entity);
        assertThat(clonedEntity.getId()).isEqualTo(entity.getId());
    }
}
