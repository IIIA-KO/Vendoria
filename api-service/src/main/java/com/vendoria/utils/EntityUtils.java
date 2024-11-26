package com.vendoria.utils;

import com.vendoria.common.entities.BaseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class EntityUtils {
    public static <T extends BaseEntity> List<T> cloneList(List<T> entities) {
        return entities.stream()
                .map(entity -> {
                    try {
                        return (T) entity.clone();
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    public static <T extends BaseEntity> List<T> sortEntities(List<T> entities) {
        return entities.stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
