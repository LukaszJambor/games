package com.example2.demo.converters;

import com.example2.demo.data.CommentData;
import com.example2.demo.data.GameData;
import com.example2.demo.model.CommentEntity;
import com.example2.demo.model.GameEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by USER on 26.05.2019.
 */

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = CommentEntityToCommentDataMapper.class)
public interface GameEntityGameDataMapper {
    @Mapping(target = "producerName", source = "producer.producerName")
    @Mapping(target = "price", source = "price.price")
    @Mapping(target = "currency", source = "price.currency")
    @Mapping(target = "commentDataList", source = "comments")
    GameData toDto(GameEntity gameEntity);

    @Mapping(source = "producerName", target = "producer.producerName")
    @Mapping(source = "price", target = "price.price")
    @Mapping(source = "currency", target = "price.currency")
    GameEntity toEntity(GameData gameData);
}