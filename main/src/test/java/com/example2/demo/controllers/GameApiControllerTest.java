package com.example2.demo.controllers;

import com.example2.demo.data.GameData;
import com.example2.demo.model.enums.Currency;
import com.example2.demo.model.enums.DistributionPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GameApiControllerTest {

    @Autowired
    private BaseTest baseTest;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldAddGame_whenBodyIsValid() throws Exception {
        mvc.perform(post("/restapi/v1/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(baseTest.getObjectAsString(withGameData())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("name")));
    }

    @Test
    public void shouldReturnGame_whenAdded() throws Exception {
        mvc.perform(post("/restapi/v1/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(baseTest.getObjectAsString(withGameData())));
        mvc.perform((get("/restapi/v1/games")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("name")));
    }

    private GameData withGameData() {
        GameData gameData = new GameData();
        gameData.setCurrency(Currency.EUR);
        gameData.setDistributionPath(DistributionPath.CD);
        gameData.setName("name");
        gameData.setPrice(new BigDecimal(10));
        gameData.setProducerName("producer name");
        gameData.setQuantity(5);
        gameData.setType("type");
        return gameData;
    }
}