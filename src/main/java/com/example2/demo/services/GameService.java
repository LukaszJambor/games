package com.example2.demo.services;

import com.example2.demo.converters.GameDataToGameEntityConverter;
import com.example2.demo.converters.GameEntityToGameDataConverter;
import com.example2.demo.dao.GameDao;
import com.example2.demo.data.GameData;
import com.example2.demo.entity.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 25.05.2019.
 */

@Component
public class GameService {

    @Autowired
    private GameDataToGameEntityConverter gameDataToGameEntityConverter;

    @Autowired
    private GameEntityToGameDataConverter gameEntityToGameDataConverter;

    @Autowired
    private GameDao gameDao;

    public void addGame(GameData gameData){
        GameEntity gameEntity = gameDataToGameEntityConverter.covert(gameData);
        gameDao.save(gameEntity);
    }

    public List<GameData> getGames() {
        Iterable<GameEntity> all = gameDao.findAll();
        List<GameData> gameDataList = new ArrayList<>();
        all.forEach(gameEntity -> convert(gameEntity, gameDataList));
        return gameDataList;
    }

    private void convert(GameEntity gameEntity, List<GameData> gameDataList) {
        gameDataList.add(gameEntityToGameDataConverter.convert(gameEntity));
    }

    public List<GameData> getGames(String name, String producer) {
        List<GameEntity> gameByNameOrProducer = gameDao.findGameByNameOrProducer(name, producer);
        List<GameData> gameDataList = new ArrayList<>();
        gameByNameOrProducer.forEach(gameEntity -> convert(gameEntity, gameDataList));
        return gameDataList;
    }
}