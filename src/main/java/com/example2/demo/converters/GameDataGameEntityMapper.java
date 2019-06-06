package com.example2.demo.converters;

import com.example2.demo.data.GameData;
import com.example2.demo.model.GameEntity;
import org.mapstruct.Mapper;

/**
 * Created by USER on 25.05.2019.
 */

@Mapper(componentModel = "spring", uses = {GameDataProducerEntityMapper.class, GameDataPriceEntityMapper.class})
public interface GameDataGameEntityMapper {
    GameEntity convert(GameData gameData);
}
