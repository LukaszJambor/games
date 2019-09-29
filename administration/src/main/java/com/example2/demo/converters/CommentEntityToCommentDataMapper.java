package com.example2.demo.converters;

import com.example2.demo.data.CommentData;
import com.example2.demo.model.CommentEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

/**
 * Created by USER on 26.05.2019.
 */

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentEntityToCommentDataMapper {
    CommentData toDto(CommentEntity commentEntity);
    CommentEntity toEntity(CommentData commentData);
}