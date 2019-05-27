package com.example2.demo.dao;

import com.example2.demo.entity.GameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by USER on 25.05.2019.
 */

@Component
public interface GameDao extends CrudRepository<GameEntity, Long> {
    public List<GameEntity> findGameByNameOrProducer(String name, String producer);
}
