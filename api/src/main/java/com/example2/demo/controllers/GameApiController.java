package com.example2.demo.controllers;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.data.GameData;
import com.example2.demo.model.GameEntity;
import com.example2.demo.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/restapi/v1")
public class GameApiController {

    private GameService gameService;
    private GameEntityGameDataMapper gameEntityGameDataMapper;

    public GameApiController(GameService gameService, GameEntityGameDataMapper gameEntityGameDataMapper) {
        this.gameService = gameService;
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
    }


    @GetMapping(path = "/games")
    @ResponseBody
    public ResponseEntity<List<GameData>> showGames(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "producer", required = false) String producer) {
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(producer)) {
            return ResponseEntity.ok(convertToData(gameService.getGames()));
        } else {
            return ResponseEntity.ok(convertToData(gameService.getGames(name, producer)));
        }
    }

    @PostMapping(path = "/game/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGame(@Valid @RequestBody GameData gameData) {
        gameService.addGame(gameEntityGameDataMapper.toEntity(gameData));
    }

    private List<GameData> convertToData(List<GameEntity> all) {
        return all.stream()
                .map(game -> gameEntityGameDataMapper.toDto(game))
                .collect(Collectors.toList());
    }
}
