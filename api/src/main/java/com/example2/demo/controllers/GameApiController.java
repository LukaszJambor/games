package com.example2.demo.controllers;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.data.GameData;
import com.example2.demo.model.GameEntity;
import com.example2.demo.services.GameService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/restapi/v1")
public class GameApiController {

    private GameService gameService;
    private GameEntityGameDataMapper gameEntityGameDataMapper;
    private PagedResourcesAssembler<GameData> pagedResourcesAssembler;

    public GameApiController(GameService gameService, GameEntityGameDataMapper gameEntityGameDataMapper,
                             PagedResourcesAssembler<GameData> pagedResourcesAssembler) {
        this.gameService = gameService;
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping(path = "/games")
    public ResponseEntity<PagedResources<Resource<GameData>>> showGames(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "producer", required = false) String producer,
                                              @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        return ResponseEntity.ok().body(pagedResourcesAssembler.toResource(gameService.getGames(name, producer, page, size)));
    }

    @GetMapping(path = "/games/{gameId}")
    public ResponseEntity<GameData> showGame(@PathVariable("gameId") long id){
        Optional<GameData> gameData = gameService.getGame(id);
        return gameData
                .map(gameDataInternal -> ResponseEntity.ok().body(gameDataInternal))
                .orElse(ResponseEntity.noContent().build());
    }

    //dodac walidacje czy taka gra juz istnieje po id, jezeli tak to inny status, jezeli nie to utworzyc
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
