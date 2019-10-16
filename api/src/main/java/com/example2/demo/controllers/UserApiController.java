package com.example2.demo.controllers;

import com.example2.demo.converters.CommentEntityToCommentDataMapper;
import com.example2.demo.converters.LendEntityToLendDataMapper;
import com.example2.demo.converters.UserEntityUserDataMapper;
import com.example2.demo.data.CommentData;
import com.example2.demo.data.LendData;
import com.example2.demo.data.UserData;
import com.example2.demo.model.CommentEntity;
import com.example2.demo.model.LendEntity;
import com.example2.demo.services.GameService;
import com.example2.demo.services.UserService;
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
    public void register(@Valid @RequestBody UserData userData) {
        userService.addUser(userEntityUserDataMapper.toEntity(userData));
    }

    @GetMapping(value = "/register/confirm/{hash}")
    public void confirm(@PathVariable("hash") String hash) throws ActivationException {
        userService.confirmAccount(hash);
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @GetMapping(value = "/user/{userId}/games")
    public ResponseEntity<List<LendData>> lendGames(@PathVariable("userId") Long userId) {
        List<LendEntity> lendEntityList = gameService.getUserGamePanel(userId);
        List<LendData> games = lendEntityList.stream()
                .map(lendEntity -> lendEntityToLendDataMapper.toDto(lendEntity))
                .collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @GetMapping(value = "/user/{userId}/lend/{gameId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLend(@PathVariable("userId") Long userId, @PathVariable("gameId") Long gameId) {
        gameService.createLend(userId, gameId);
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @GetMapping(value = "/user/{userId}/return/{gameId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReturn(@PathVariable("userId") Long userId, @PathVariable("gameId") Long gameId) {
        gameService.createReturn(userId, gameId);
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @RequestMapping(value = "/user/{userId}/addComment", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void commentView(@PathVariable("userId") Long userId, @RequestBody CommentData commentData) {
        CommentEntity commentEntity = commentEntityToCommentDataMapper.toEntity(commentData);
        gameService.createComment(commentEntity);
    }
}