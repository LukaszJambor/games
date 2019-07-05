package com.example2.demo.controllers;

import com.example2.demo.data.UserData;
import com.example2.demo.exception.UserFoundException;
import com.example2.demo.model.UserEntity;
import com.example2.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.rmi.activation.ActivationException;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
        try {
            userService.addUser(userData);
        } catch (UserFoundException e) {
            System.out.println(e);
            model.addAttribute("userExist", "User Exists");
            return "/register";
        }
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
}