package com.example2.demo.controllers;

import com.example2.demo.data.UserData;
import com.example2.demo.model.UserEntity;
import com.example2.demo.response.JwtResponse;
import com.example2.demo.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BaseTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationController authenticationController;

    public UserEntity persistUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test@test.pl");
        userEntity.setPassword("testpass");
        return userService.addUser(userEntity);
    }

    public String getToken() throws Exception {
        UserData userData = new UserData();
        userData.setLogin("test@test.pl");
        userData.setPassword("testpass");
        ResponseEntity<JwtResponse> authenticationToken = authenticationController.createAuthenticationToken(userData);
        return authenticationToken.getBody().getToken();
    }

    public String getObjectAsString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}
