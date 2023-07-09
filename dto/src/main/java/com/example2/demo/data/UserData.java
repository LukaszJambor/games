package com.example2.demo.data;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;

@Setter
@Getter
public class UserData {

    @Email(message = "should be email")
    private String login;

    private String password;
}