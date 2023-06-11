package com.example2.demo.services;

import com.example2.demo.converters.CommentEntityToCommentDataMapper;
import com.example2.demo.dao.CommentRepository;
import com.example2.demo.dao.projections.ShortComment;
import com.example2.demo.data.CommentData;
import com.example2.demo.model.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<ShortComment> getShortComments(Long id) {
        return commentRepository.findShortCommentEntitiesByGameIdShort(id);
    }

    public Page<CommentEntity> getAllUserComments(Long userId, Integer page, Integer size) {
        return commentRepository.findCommentEntityByUserId(userId, PageRequest.of(page, size));
    }
}
