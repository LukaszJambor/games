package com.example2.demo.controllers;

import com.example2.demo.data.UserData;
import com.example2.demo.model.UserEntity;
import com.example2.demo.model.enums.ActivationType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserApiControllerTest {

    @Autowired
    private BaseTest baseTest;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldRegisterUser_whenDataIsOk() throws Exception {
        UserData userData = new UserData();
        userData.setLogin("test@test.pl");
        userData.setPassword("testpass");

        mvc.perform(post("/restapi/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(baseTest.getObjectAsString(userData)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.login", is("test@test.pl")));
    }

    @Test
    public void shouldAcceptHash() throws Exception {
        UserEntity userEntity = baseTest.persistUser();
        String lastHash = userEntity.getLastHash(ActivationType.EMAIL);
        mvc.perform(get("/restapi/v1/register/confirm/" + lastHash)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnEmptyGamesList_whenLogged() throws Exception {
        UserEntity userEntity = baseTest.persistUser();
        String token = baseTest.getToken();
        mvc.perform(get("/restapi/v1/users/" + userEntity.getId() + "/games")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnForbiddenAccess_whenNoBearer() throws Exception {
        UserEntity userEntity = baseTest.persistUser();
        mvc.perform(get("/restapi/v1/users/" + userEntity.getId() + "/games")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}