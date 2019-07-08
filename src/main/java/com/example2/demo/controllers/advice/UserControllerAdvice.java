package com.example2.demo.controllers.advice;

import com.example2.demo.data.UserData;
import com.example2.demo.exception.NotEnoughCopiesException;
import com.example2.demo.exception.UserFoundException;
import com.mchange.util.DuplicateElementException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

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
    public String informationAboutEmptyStock(Model model) {
        return "redirect:/?available=false";
    }

    @ExceptionHandler(value = DuplicateElementException.class)
    public String informationAboutLendGame(Model model) {
        return "redirect:/?doubleLend=true";
    }
}