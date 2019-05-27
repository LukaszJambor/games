package com.example2.demo.converters;

import com.example2.demo.data.GameData;
import com.example2.demo.entity.GameEntity;
import org.springframework.stereotype.Component;

/**
 * Created by USER on 26.05.2019.
 */
@Component
public class GameEntityToGameDataConverter {

    public GameData convert(GameEntity gameEntity) {
        GameData gameData = new GameData();
        gameData.setId(gameEntity.getId());
        gameData.setName(gameEntity.getName());
        gameData.setType(gameEntity.getType());
        gameData.setProducer(gameEntity.getProducer());
        gameData.setDistributionPath(gameEntity.getDistributionPath());
        return gameData;
    }
}