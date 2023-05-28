package com.example2.demo.controllers;

import com.example2.demo.converters.CommentEntityToCommentDataMapper;
import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.converters.LendEntityToLendDataMapper;
import com.example2.demo.converters.UserEntityUserDataMapper;
import com.example2.demo.data.CommentData;
import com.example2.demo.data.GameData;
import com.example2.demo.data.LendData;
import com.example2.demo.data.UserData;
import com.example2.demo.exception.ActivationException;
import com.example2.demo.model.CommentEntity;
import com.example2.demo.model.GameEntity;
import com.example2.demo.model.LendEntity;
import com.example2.demo.model.UserEntity;
import com.example2.demo.services.GameService;
import com.example2.demo.services.LendService;
import com.example2.demo.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.example2.demo.utils.LinkAssembler.generateGameLink;

@RestController
@RequestMapping(path = "/restapi/v1")
public class UserApiController {

    private final UserService userService;
    private final GameService gameService;
    private final UserEntityUserDataMapper userEntityUserDataMapper;
    private final LendEntityToLendDataMapper lendEntityToLendDataMapper;
    private final CommentEntityToCommentDataMapper commentEntityToCommentDataMapper;
    private final LendService lendService;
    private final GameEntityGameDataMapper gameEntityGameDataMapper;
    private final PagedResourcesAssembler<GameData> pagedResourcesAssembler;

    public UserApiController(UserService userService, GameService gameService, UserEntityUserDataMapper userEntityUserDataMapper,
                             LendEntityToLendDataMapper lendEntityToLendDataMapper, CommentEntityToCommentDataMapper commentEntityToCommentDataMapper,
                             LendService lendService, GameEntityGameDataMapper gameEntityGameDataMapper,
                             PagedResourcesAssembler<GameData> pagedResourcesAssembler) {
        this.userService = userService;
        this.gameService = gameService;
        this.userEntityUserDataMapper = userEntityUserDataMapper;
        this.lendEntityToLendDataMapper = lendEntityToLendDataMapper;
        this.commentEntityToCommentDataMapper = commentEntityToCommentDataMapper;
        this.lendService = lendService;
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<UserData> register(@Valid @RequestBody UserData userData) {
        UserEntity userEntity = userService.addUser(userEntityUserDataMapper.toEntity(userData));
        UserData userResource = userEntityUserDataMapper.toDto(userEntity);
        return new Resource<>(userResource);
    }

    @GetMapping(value = "/register/confirm/{hash}")
    public void confirm(@PathVariable("hash") String hash) throws ActivationException {
        userService.confirmAccount(hash);
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @GetMapping(value = "/users/{userId}/games")
    public ResponseEntity<List<LendData>> lendGames(@PathVariable("userId") Long userId) {
        List<LendEntity> lendEntityList = gameService.getUserGamePanel(userId);
        List<LendData> games = lendEntityList.stream()
                .map(lendEntityToLendDataMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @PostMapping(value = "/users/{userId}/lends/{gameId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<LendData> createLend(@PathVariable("userId") Long userId, @PathVariable("gameId") Long gameId) {
        LendEntity lend = gameService.createLend(userId, gameId);
        LendData lendResource = lendEntityToLendDataMapper.toDto(lend);
        Link selfLink = ControllerLinkBuilder
                .linkTo(UserApiController.class)
                .slash("users/" + userId + "/lends/" + lendResource.getId())
                .withSelfRel();
        return new Resource<>(lendResource, selfLink);
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @PutMapping(value = "/users/{userId}/returns/{gameId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Resource<LendData> createReturn(@PathVariable("userId") Long userId, @PathVariable("gameId") Long gameId) {
        LendEntity aReturn = gameService.createReturn(userId, gameId);
        LendData returnResource = lendEntityToLendDataMapper.toDto(aReturn);
        Link selfLink = ControllerLinkBuilder
                .linkTo(UserApiController.class)
                .slash("users/" + userId + "/returns/" + returnResource.getId())
                .withSelfRel();
        return new Resource<>(returnResource, selfLink);
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @PostMapping(value = "/users/{userId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<CommentData> commentView(@PathVariable("userId") Long userId, @RequestBody CommentData commentData) {
        CommentEntity commentEntity = commentEntityToCommentDataMapper.toEntity(commentData);
        CommentEntity comment = gameService.createComment(commentEntity);
        CommentData commentResource = commentEntityToCommentDataMapper.toDto(comment);
        Link selfLink = ControllerLinkBuilder
                .linkTo(UserApiController.class)
                .slash("users/" + userId + "/comments/" + commentResource.getId())
                .withSelfRel();
        return new Resource<>(commentResource, selfLink);
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @GetMapping(value = "users/{userId}/lendHistory")
    public ResponseEntity<PagedResources<Resource<GameData>>> getHistoryLends(@PathVariable("userId") Long userId,
                                                                              @RequestParam("page") Integer page,
                                                                              @RequestParam("size") Integer size) {
        Page<GameEntity> historyLendGames = lendService.getHistoryLendGames(userId, page, size);
        Page<GameData> gameData = convertToData(historyLendGames);
        return ResponseEntity.ok(pagedResourcesAssembler.toResource(gameData));
    }

    private Page<GameData> convertToData(Page<GameEntity> all) {
        List<GameData> gamesData = all.stream()
                .map(gameEntityGameDataMapper::toDto)
                .collect(Collectors.toList());
        gamesData.forEach(gameData -> gameData.setLink(generateGameLink(gameData.getId())));
        return new PageImpl<>(gamesData, all.getPageable(), all.getTotalPages());
    }
}