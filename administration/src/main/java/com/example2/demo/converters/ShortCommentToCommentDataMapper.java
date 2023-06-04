package com.example2.demo.converters;

import com.example2.demo.dao.projections.ShortComment;
import com.example2.demo.data.CommentData;
import com.example2.demo.model.CommentEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by USER on 26.05.2019.
 */

@Component
public class ShortCommentToCommentDataMapper {
    public CommentData toDto(ShortComment shortComment) {
        CommentData commentData = new CommentData();
        commentData.setComment(shortComment.getComment());
        commentData.setLogin(shortComment.getLogin());
        return commentData;
    }
}