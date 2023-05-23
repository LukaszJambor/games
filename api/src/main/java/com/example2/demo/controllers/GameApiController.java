package com.example2.demo.controllers;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.data.GameData;
import com.example2.demo.model.GameEntity;
import com.example2.demo.services.GameService;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/restapi/v1")
public class GameApiController {

    private GameService gameService;
    private GameEntityGameDataMapper gameEntityGameDataMapper;

    public GameApiController(GameService gameService, GameEntityGameDataMapper gameEntityGameDataMapper) {
        this.gameService = gameService;
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/games")
    public Page<GameData> showGames(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "producer", required = false) String producer,
                                          @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        return gameService.getGames(name, producer, page, size);
    }

    @PostMapping(path = "/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<GameData> addGame(@Valid @RequestBody GameData gameData) {
        GameEntity gameEntity = gameService.addGame(gameEntityGameDataMapper.toEntity(gameData));
        GameData gameResource = gameEntityGameDataMapper.toDto(gameEntity);
        Link selfLink = ControllerLinkBuilder
                .linkTo(GameApiController.class)
                .slash("games/" + gameResource.getId())
                .withSelfRel();
        return new Resource<>(gameResource, selfLink);
    }
}
