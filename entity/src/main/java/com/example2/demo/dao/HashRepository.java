package com.example2.demo.dao;

import com.example2.demo.model.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HashRepository extends JpaRepository<UserTokenEntity, Long> {
    UserTokenEntity findActivationEntityByHashAndActivationTimestampIsNull(String hash);

    List<UserTokenEntity> findActivationEntityByCreationTimestampBeforeAndActivationTimestampIsNull(LocalDateTime creationTimestamp);
}