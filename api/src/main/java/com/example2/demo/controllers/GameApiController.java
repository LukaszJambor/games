package com.example2.demo.controllers;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.data.GameData;
import com.example2.demo.model.GameEntity;
import com.example2.demo.services.GameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example2.demo.utils.LinkAssembler.generateGameLink;

@RestController
@RequestMapping(path = "/restapi/v1")
public class GameApiController {

    private final GameService gameService;
    private final GameEntityGameDataMapper gameEntityGameDataMapper;
    private final PagedResourcesAssembler<GameData> pagedResourcesAssembler;

    public GameApiController(GameService gameService, GameEntityGameDataMapper gameEntityGameDataMapper,
                             PagedResourcesAssembler<GameData> pagedResourcesAssembler) {
        this.gameService = gameService;
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping(path = "/games")
    public ResponseEntity<PagedResources<Resource<GameData>>> showGames(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "producer", required = false) String producer,
                                                                        @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        Page<GameEntity> games = gameService.getGames(name, producer, page, size);
        Page<GameData> gameData = convertToData(games);
        return ResponseEntity.ok().body(pagedResourcesAssembler.toResource(gameData));
    }

    @GetMapping(path = "/games/{gameId}")
    public ResponseEntity<GameData> showGame(@PathVariable("gameId") long id) {
        Optional<GameData> gameData = gameService.getGame(id);
        gameData.ifPresent(game -> game.setLink(generateGameLink(game.getId())));
        return gameData
                .map(gameDataInternal -> ResponseEntity.ok().body(gameDataInternal))
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping(path = "/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Resource<GameData>> addGame(@Valid @RequestBody GameData gameData) {
        GameEntity gameEntity = gameService.addGame(gameEntityGameDataMapper.toEntity(gameData));
        if (gameEntity.getId() != null) {
            GameData gameResource = gameEntityGameDataMapper.toDto(gameEntity);
            return ResponseEntity.ok().body(new Resource<>(gameResource, generateGameLink(gameResource.getId())));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    private Page<GameData> convertToData(Page<GameEntity> all) {
        List<GameData> gamesData = all.stream()
                .map(game -> {
                    GameData gameData = gameEntityGameDataMapper.toDto(game);
                    gameData.setLink(generateGameLink(game.getId()));
                    return gameData;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(gamesData, all.getPageable(), all.getTotalPages());
    }
}
