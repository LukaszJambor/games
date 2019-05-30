package com.example2.demo.services;

import com.example2.demo.converters.GameDataToGameEntityConverter;
import com.example2.demo.converters.GameEntityToGameDataConverter;
import com.example2.demo.dao.GameRepository;
import com.example2.demo.data.GameData;
import com.example2.demo.model.GameEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by USER on 25.05.2019.
 */

@Component
public class GameService {

    private GameDataToGameEntityConverter gameDataToGameEntityConverter;
    private GameEntityToGameDataConverter gameEntityToGameDataConverter;
    private GameRepository gameRepository;

    public GameService(GameDataToGameEntityConverter gameDataToGameEntityConverter, GameEntityToGameDataConverter gameEntityToGameDataConverter,
                       GameRepository gameRepository) {
        this.gameDataToGameEntityConverter = gameDataToGameEntityConverter;
        this.gameEntityToGameDataConverter = gameEntityToGameDataConverter;
        this.gameRepository = gameRepository;
    }

    public void addGame(GameData gameData) {
        GameEntity gameEntity = gameDataToGameEntityConverter.covert(gameData);
        gameRepository.save(gameEntity);
    }

    public List<GameData> getGames() {
        List<GameEntity> all = gameRepository.findAll();
        return convertToData(all);
    }

    public List<GameData> getGames(String name, String producer) {
        List<GameEntity> gameByNameOrProducer = gameRepository.findGameByNameOrProducer(name, producer);
        return convertToData(gameByNameOrProducer);
    }

    private List<GameData> convertToData(List<GameEntity> all) {
        return all.stream()
                .map(game -> gameEntityToGameDataConverter.convert(game))
                .collect(Collectors.toList());
    }
}