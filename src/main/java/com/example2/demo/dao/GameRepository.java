package com.example2.demo.dao;

import com.example2.demo.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by USER on 25.05.2019.
 */

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long>, JpaSpecificationExecutor {
    List<GameEntity> findGameEntityByNameOrProducerEntity_ProducerName(String name, String producer);

    @Query("select g from GameEntity g where g.name=:name or g.producerEntity.producerName=:producerName")
    List<GameEntity> findGameByNameOrProducerName(@Param("name") String name, @Param("producerName") String producer);
}
