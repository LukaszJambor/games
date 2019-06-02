package com.example2.demo.converters;

import com.example2.demo.data.GameData;
import com.example2.demo.model.GameEntity;
import org.springframework.stereotype.Component;

/**
 * Created by USER on 25.05.2019.
 */

@Component
public class GameDataToGameEntityConverter {

    private GameDataToProducerEntityConverter gameDataToProducerEntityConverter;
    private GameDataToPriceEntityConverter gameDataToPriceEntityConverter;

    public GameDataToGameEntityConverter(GameDataToProducerEntityConverter gameDataToProducerEntityConverter, GameDataToPriceEntityConverter gameDataToPriceEntityConverter) {
        this.gameDataToPriceEntityConverter = gameDataToPriceEntityConverter;
        this.gameDataToProducerEntityConverter = gameDataToProducerEntityConverter;
    }

    public GameEntity covert(GameData gameData) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(gameData.getId());
        gameEntity.setName(gameData.getName());
        gameEntity.setType(gameData.getType());
        gameEntity.setProducerEntity(gameDataToProducerEntityConverter.convert(gameData));
        gameEntity.setDistributionPath(gameData.getDistributionPath());
        gameEntity.setPriceEntity(gameDataToPriceEntityConverter.convert(gameData));
        return gameEntity;
    }
}
