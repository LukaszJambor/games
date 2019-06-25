package com.example2.demo.converters;

import com.example2.demo.data.GameData;
import com.example2.demo.model.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by USER on 26.05.2019.
 */

@Mapper(componentModel = "spring")
public interface GameEntityGameDataMapper {
    @Mapping(target = "producerName", source = "producerEntity.producerName")
    @Mapping(target = "price", source = "priceEntity.price")
    @Mapping(target = "currency", source = "priceEntity.currency")
    GameData toDto(GameEntity gameEntity);

    @Mapping(source = "producerName", target = "producerEntity.producerName")
    @Mapping(source = "price", target = "priceEntity.price")
    @Mapping(source = "currency", target = "priceEntity.currency")
    GameEntity toEntity(GameData gameData);
}