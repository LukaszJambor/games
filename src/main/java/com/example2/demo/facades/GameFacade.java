package com.example2.demo.facades;

import com.example2.demo.data.GameData;
import com.example2.demo.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by USER on 25.05.2019.
 */

@Component
public class GameFacade {

    private GameService gameService;

    @Autowired
    public GameFacade(GameService gameService) {
        this.gameService = gameService;
    }

    public void addGame(GameData gameData) {
        gameService.addGame(gameData);
    }

    public List<GameData> getGames() {
        return gameService.getGames();
    }

    public List<GameData> getGames(String name, String producer) {
        return gameService.getGames(name, producer);
    }
}
