package com.example2.demo.controllers;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.data.GameData;
import com.example2.demo.data.QueryData;
import com.example2.demo.model.GameEntity;
import com.example2.demo.services.GameService;
import com.example2.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by USER on 24.05.2019.
 */
@Controller
public class GameController {

    private GameService gameService;
    private UserService userService;
    private GameEntityGameDataMapper gameEntityGameDataMapper;

    public GameController(GameService gameService, UserService userService, GameEntityGameDataMapper gameEntityGameDataMapper) {
        this.gameService = gameService;
        this.userService = userService;
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
    }

    @RequestMapping(value = "/addGame", method = RequestMethod.GET)
    public String addGameView(Model model) {
        model.addAttribute("gameData", new GameData());
        return "addGame";
    }

    @RequestMapping(value = "/addGame", method = RequestMethod.POST)
    public String addGame(@ModelAttribute("gameData") GameData gameData) {
        gameService.addGame(gameEntityGameDataMapper.toEntity(gameData));
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showGames(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "producer", required = false) String producer, Model model) {
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(producer)) {
            model.addAttribute("games", convertToData(gameService.getGames()));
        } else {
            model.addAttribute("games", convertToData(gameService.getGames(name, producer)));
        }
        model.addAttribute("userId", userService.getLoggedUserId());
        return "listing";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchView(Model model) {
        model.addAttribute("queryData", new QueryData());
        return "search";
    }

    private List<GameData> convertToData(List<GameEntity> all) {
        return all.stream()
                .map(game -> gameEntityGameDataMapper.toDto(game))
                .collect(Collectors.toList());
    }
}