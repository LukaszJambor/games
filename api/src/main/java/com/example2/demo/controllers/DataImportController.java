package com.example2.demo.controllers;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.data.GameData;
import com.example2.demo.model.GameEntity;
import com.example2.demo.services.GameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/import")
public class DataImportController {

    private GameService gameService;
    private GameEntityGameDataMapper gameEntityGameDataMapper;

    public DataImportController(GameService gameService, GameEntityGameDataMapper gameEntityGameDataMapper) {
        this.gameService = gameService;
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
    }

    @PostMapping(path = "/games")
    public void aaa(@RequestBody List<GameData> gameDataList) {
        gameDataList.stream()
                .forEach(gameData -> {
                    GameEntity gameEntity = gameEntityGameDataMapper.toEntity(gameData);
                    gameService.addGame(gameEntity);
                });
    }
}
