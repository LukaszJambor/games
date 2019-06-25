package com.example2.demo.dao;

import com.example2.demo.model.ActivationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashRepository extends JpaRepository<ActivationEntity, Long> {
    ActivationEntity findActivationEntityByHash(String hash);
}