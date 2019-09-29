package com.example2.demo.dao.specifications;

import com.example2.demo.model.GameEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Created by USER on 02.06.2019.
 */
@Component
public class GameSpecification {

    public Specification<GameEntity> findGameEntityByNameOrProducerName(String name, String producer) {
        return where(gameNameIs(name).
                or(gameProducerNameIs(producer)));
    }

    private Specification<GameEntity> gameProducerNameIs(String producerName) {
        return userAttributeContains("producerName", producerName);
    }

    private Specification<GameEntity> gameNameIs(String name) {
        return userAttributeContains("name", name);
    }

    private Specification<GameEntity> userAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            if ("producerName".equals(attribute)) {
               return cb.like(root.get("producer").get(attribute), value);
            }

            return cb.like(root.get(attribute), value);
        };
    }
}
