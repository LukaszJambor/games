package com.example2.demo.controllers;

import com.example2.demo.data.GameData;
import com.example2.demo.data.QueryData;
import com.example2.demo.services.GameService;
import com.example2.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by USER on 24.05.2019.
 */
@Controller
public class GameController {

    private GameService gameService;
    private UserService userService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @RequestMapping(value = "/addGame", method = RequestMethod.GET)
    public String addGameView(Model model) {
        model.addAttribute("gameData", new GameData());
        return "addGame";
    }

    @RequestMapping(value = "/addGame", method = RequestMethod.POST)
    public String addGame(@ModelAttribute("gameData") GameData gameData) {
        gameService.addGame(gameData);
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showGames(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "producer", required = false) String producer,
                            @RequestParam(value = "available", required = false) boolean available, @RequestParam(value = "doubleLend", required = false) boolean doubleLend,
                            Model model) {
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(producer)) {
            model.addAttribute("games", gameService.getGames());
        } else {
            model.addAttribute("games", gameService.getGames(name, producer));
        }
        if (available) {
            model.addAttribute("notAvailable", "game is not available");
        }
        if (doubleLend) {
            model.addAttribute("doubleLend", "you cant lend twice same game");
        }
        model.addAttribute("userId", userService.getLoggedUserId());
        return "listing";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchView(Model model) {
        model.addAttribute("queryData", new QueryData());
        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@ModelAttribute("queryData") QueryData queryData) {
        return "redirect:/?name=" + queryData.getName() + "&producer=" + queryData.getProducer();
    }
}