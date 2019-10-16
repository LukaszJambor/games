package com.example2.demo.controllers;

import com.example2.demo.converters.CommentEntityToCommentDataMapper;
import com.example2.demo.converters.LendEntityToLendDataMapper;
import com.example2.demo.converters.UserEntityUserDataMapper;
import com.example2.demo.data.CommentData;
import com.example2.demo.data.LendData;
import com.example2.demo.data.UserData;
import com.example2.demo.exception.ActivationException;
import com.example2.demo.model.CommentEntity;
import com.example2.demo.model.LendEntity;
import com.example2.demo.model.UserEntity;
import com.example2.demo.services.GameService;
import com.example2.demo.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private UserService userService;
    private GameService gameService;
    private UserEntityUserDataMapper userEntityUserDataMapper;
    private LendEntityToLendDataMapper lendEntityToLendDataMapper;
    private CommentEntityToCommentDataMapper commentEntityToCommentDataMapper;

    public UserController(UserService userService, GameService gameService, UserEntityUserDataMapper userEntityUserDataMapper,
                          LendEntityToLendDataMapper lendEntityToLendDataMapper, CommentEntityToCommentDataMapper commentEntityToCommentDataMapper) {
        this.userService = userService;
        this.gameService = gameService;
        this.userEntityUserDataMapper = userEntityUserDataMapper;
        this.lendEntityToLendDataMapper = lendEntityToLendDataMapper;
        this.commentEntityToCommentDataMapper = commentEntityToCommentDataMapper;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "loginForm";
    }

    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public String fail() {
        return "fail";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "success";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerView(Model model) {
        model.addAttribute("userData", new UserData());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("userData") UserData userData, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            return "/register";
        }
        userService.addUser(userEntityUserDataMapper.toEntity(userData));
        return "redirect:/";
    }

    @RequestMapping(value = "/register/confirm/{hash}", method = RequestMethod.GET)
    public String confirm(@PathVariable("hash") String hash) {
        try {
            userService.confirmAccount(hash);
        } catch (ActivationException e) {
            return "fail";
        }
        return "confirmationSuccessfull";
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @RequestMapping(value = "/user/{userId}/games", method = RequestMethod.GET)
    public String lendGames(@PathVariable("userId") Long userId, Model model) {
        List<LendEntity> lendEntityList = gameService.getUserGamePanel(userId);
        List<LendData> games = lendEntityList.stream()
                .map(lendEntity -> lendEntityToLendDataMapper.toDto(lendEntity))
                .collect(Collectors.toList());
        model.addAttribute("games", games);
        model.addAttribute("userId", userId);
        model.addAttribute("gameId", model.asMap().get("gameId"));
        model.addAttribute("commentData", new CommentData());
        return "userLendGames";
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @RequestMapping(value = "/user/{userId}/lend/{gameId}", method = RequestMethod.GET)
    public String createLend(@PathVariable("userId") Long userId, @PathVariable("gameId") Long gameId) {
        gameService.createLend(userId, gameId);
        return "redirect:/user/" + userId + "/games";
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @RequestMapping(value = "/user/{userId}/return/{gameId}", method = RequestMethod.GET)
    public String createReturn(@PathVariable("userId") Long userId, @PathVariable("gameId") Long gameId, RedirectAttributes redirectAttrs) {
        gameService.createReturn(userId, gameId);
        redirectAttrs.addFlashAttribute("gameId", gameId);
        return "redirect:/user/" + userId + "/games";
    }

    @RequestMapping(value = "/user/{userId}/game/{gameId}/addComment", method = RequestMethod.POST)
    public String commentView(@PathVariable("userId") Long userId, @PathVariable("gameId") Long gameId,
                              @ModelAttribute("commentData") CommentData commentData) {
        CommentEntity commentEntity = commentEntityToCommentDataMapper.toEntity(commentData);
        commentEntity.setGameKey(gameId);
        gameService.createComment(commentEntity);
        return "redirect:/user/" + userId + "/games";
    }
}