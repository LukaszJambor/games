package com.example2.demo.controllers.advice;

import com.example2.demo.data.UserData;
import com.example2.demo.exception.DuplicatedLendException;
import com.example2.demo.exception.NotEnoughCopiesException;
import com.example2.demo.exception.UserFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(value = UserFoundException.class)
    public ModelAndView redirectWhenUserExists() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userData", new UserData());
        modelAndView.addObject("userExist", "User Exists");
        modelAndView.setViewName("/register");
        return modelAndView;
    }

    @ExceptionHandler(value = NotEnoughCopiesException.class)
    public String informationAboutEmptyStock(RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("notAvailable", "copy currently unavailable");
        return "redirect:/";
    }

    @ExceptionHandler(value = DuplicatedLendException.class)
    public String informationAboutLendGame(RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("doubleLend", "you cant lend twice same game");
        return "redirect:/";
    }
}