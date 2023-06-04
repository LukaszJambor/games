package com.example2.demo.services;

import com.example2.demo.dao.CommentRepository;
import com.example2.demo.dao.projections.ShortComment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<ShortComment> getShortComments(Long id) {
        return commentRepository.findShortCommentEntitiesByGameIdShort(id);
    }
}
