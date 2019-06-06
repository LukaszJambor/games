package com.example2.demo.converters;

import com.example2.demo.data.GameData;
import com.example2.demo.model.ProducerEntity;
import org.mapstruct.Mapper;

/**
 * Created by USER on 31.05.2019.
 */
@Mapper(componentModel = "spring")
public interface GameDataProducerEntityMapper {
    ProducerEntity convert(GameData gameData);
}
