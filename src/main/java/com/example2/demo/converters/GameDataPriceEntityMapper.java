package com.example2.demo.converters;

import com.example2.demo.data.GameData;
import com.example2.demo.model.PriceEntity;
import org.mapstruct.Mapper;

/**
 * Created by USER on 01.06.2019.
 */
@Mapper(componentModel = "spring")
public interface GameDataPriceEntityMapper {
    PriceEntity convert(GameData gameData);
}
