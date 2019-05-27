package com.example2.demo.converters;

import com.example2.demo.data.GameData;
import com.example2.demo.entity.GameEntity;
import org.springframework.stereotype.Component;

/**
 * Created by USER on 25.05.2019.
 */

@Component
public class GameDataToGameEntityConverter {

    public GameEntity covert(GameData gameData) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(gameData.getId());
        gameEntity.setName(gameData.getName());
        gameEntity.setType(gameData.getType());
        gameEntity.setProducer(gameData.getProducer());
        gameEntity.setDistributionPath(gameData.getDistributionPath());
        return gameEntity;
    }
}
