package com.example2.demo.dao;

import com.example2.demo.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findCommentEntityByUuid(String uuid);

    Optional<CommentEntity> findCommentEntityByUserIdAndGameKey(Long userId, Long gameKey);
}
