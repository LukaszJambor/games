package com.example2.demo.dao;

import com.example2.demo.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by USER on 25.05.2019.
 */

@Component
public interface GameRepository extends CrudRepository<GameEntity, Long>, JpaRepository<GameEntity, Long> {
    public List<GameEntity> findGameByNameOrProducer(String name, String producer);
}
