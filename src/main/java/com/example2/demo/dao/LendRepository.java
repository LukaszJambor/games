package com.example2.demo.dao;

import com.example2.demo.model.LendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LendRepository extends JpaRepository<LendEntity, Long> {

    List<LendEntity> findByUserEntityId(Long userId);

    Optional<LendEntity> findByUserEntityIdAndGameEntityIdAndLendEndDateIsNull(Long userId, Long gameId);
}
