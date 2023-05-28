package com.example2.demo.dao;

import com.example2.demo.model.GameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by USER on 25.05.2019.
 */

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long>, JpaSpecificationExecutor {

    @Query("SELECT l.game from LendEntity l where l.user.id = :userId")
    Page<GameEntity> findHistoricalLendGamesByUser(Long userId, Pageable pageable);
}
