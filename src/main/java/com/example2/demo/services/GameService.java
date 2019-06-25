package com.example2.demo.services;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.dao.GameRepository;
import com.example2.demo.dao.specifications.GameSpecification;
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

    private GameEntityGameDataMapper gameEntityGameDataMapper;
    private GameRepository gameRepository;
    private GameSpecification gameSpecification;

    public GameService(GameEntityGameDataMapper gameEntityGameDataMapper,
                       GameRepository gameRepository, GameSpecification gameSpecification) {
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
        this.gameRepository = gameRepository;
        this.gameSpecification = gameSpecification;
    }

    public void addGame(GameData gameData) {
        GameEntity gameEntity = gameEntityGameDataMapper.toEntity(gameData);
        gameRepository.save(gameEntity);
    }

    public List<GameData> getGames() {
        List<GameEntity> all = gameRepository.findAll();
        return convertToData(all);
    }

    public List<GameData> getGames(String name, String producer) {
        List<GameEntity> gameByNameOrProducer = gameRepository.findAll(gameSpecification.findGameEntityByNameOrProducerName(name, producer));
        return convertToData(gameByNameOrProducer);
    }

    private List<GameData> convertToData(List<GameEntity> all) {
        return all.stream()
                .map(game -> gameEntityGameDataMapper.toDto(game))
                .collect(Collectors.toList());
    }
}