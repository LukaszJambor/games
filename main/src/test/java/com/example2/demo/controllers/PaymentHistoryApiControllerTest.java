package com.example2.demo.controllers;

import com.example2.demo.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PaymentHistoryApiControllerTest {

    @Autowired
    private BaseTest baseTest;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturn200_whenUserIsAvailable() throws Exception {
        UserEntity userEntity = baseTest.persistUser();
        String token = baseTest.getToken();

        mvc.perform(get("/restapi/v1/users/" + userEntity.getId() + "/histories")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}