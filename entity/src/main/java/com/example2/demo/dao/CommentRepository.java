package com.example2.demo.dao;

import com.example2.demo.dao.projections.ShortComment;
import com.example2.demo.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findCommentEntityByUuid(String uuid);

    Optional<CommentEntity> findCommentEntityByUserIdAndGameKey(Long userId, Long gameKey);

    @Query("SELECT u.login as login, c.comment as comment FROM CommentEntity c join UserEntity u on c.user = u " +
            "where c.gameKey = :gameId order by c.id desc")
    List<ShortComment> findShortCommentEntitiesByGameIdShort (Long gameId);

    @Query("SELECT c FROM CommentEntity c join UserEntity u on c.user = u " +
            "where c.user.id = :userId and c.id = :commentId")
    Optional<CommentEntity> findCommentEntityByUserIdAndCommentId(Long userId, Long commentId);
}
