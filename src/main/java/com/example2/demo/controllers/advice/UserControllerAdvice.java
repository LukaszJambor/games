package com.example2.demo.controllers.advice;

import com.example2.demo.data.UserData;
import com.example2.demo.exception.UserFoundException;
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
}
