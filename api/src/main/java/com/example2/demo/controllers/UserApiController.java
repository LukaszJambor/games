package com.example2.demo.controllers;

import com.example2.demo.converters.CommentEntityToCommentDataMapper;
import com.example2.demo.converters.LendEntityToLendDataMapper;
import com.example2.demo.converters.UserEntityUserDataMapper;
import com.example2.demo.data.CommentData;
import com.example2.demo.data.LendData;
import com.example2.demo.data.UserData;
import com.example2.demo.model.CommentEntity;
import com.example2.demo.model.LendEntity;
import com.example2.demo.model.UserEntity;
import com.example2.demo.services.GameService;
import com.example2.demo.services.UserService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.activation.ActivationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/restapi/v1")
public class UserApiController {

    private UserService userService;
    private GameService gameService;
    private UserEntityUserDataMapper userEntityUserDataMapper;
    private LendEntityToLendDataMapper lendEntityToLendDataMapper;
    private CommentEntityToCommentDataMapper commentEntityToCommentDataMapper;

    public UserApiController(UserService userService, GameService gameService, UserEntityUserDataMapper userEntityUserDataMapper,
                             LendEntityToLendDataMapper lendEntityToLendDataMapper, CommentEntityToCommentDataMapper commentEntityToCommentDataMapper) {
        this.userService = userService;
        this.gameService = gameService;
        this.userEntityUserDataMapper = userEntityUserDataMapper;
        this.lendEntityToLendDataMapper = lendEntityToLendDataMapper;
        this.commentEntityToCommentDataMapper = commentEntityToCommentDataMapper;
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
                .map(lendEntity -> lendEntityToLendDataMapper.toDto(lendEntity))
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
}