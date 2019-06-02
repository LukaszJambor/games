package com.example2.demo.services;

import com.example2.demo.converters.GameDataToGameEntityConverter;
import com.example2.demo.converters.GameEntityToGameDataConverter;
import com.example2.demo.dao.GameRepository;
import com.example2.demo.data.GameData;
import com.example2.demo.model.DistributionPath;
import com.example2.demo.model.GameEntity;
import com.example2.demo.model.ProducerEntity;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by USER on 28.05.2019.
 */
public class GameServiceTest {

    @Mock
    private GameDataToGameEntityConverter gameDataToGameEntityConverter;

    @Mock
    private GameEntityToGameDataConverter gameEntityToGameDataConverter;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddGame() {
        //given
        when(gameDataToGameEntityConverter.covert(any())).thenReturn(new GameEntity());

        //when
        gameService.addGame(new GameData());

        //then
        Mockito.verify(gameRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void shouldGetAllGames() {
        //given
        GameEntity gameEntity1 = new GameEntity();
        GameEntity gameEntity2 = new GameEntity();
        setEntityData(gameEntity1, "test1", "test2", DistributionPath.KEY);
        setEntityData(gameEntity2, "test4", "test5", DistributionPath.CD);
        GameData gameData1 = new GameData();
        GameData gameData2 = new GameData();
        setGameData(gameData1, "test1", "test2", DistributionPath.KEY);
        setGameData(gameData2, "test4", "test5", DistributionPath.CD);
        when(gameRepository.findAll()).thenReturn(Arrays.asList(gameEntity1, gameEntity2));
        when(gameEntityToGameDataConverter.convert(gameEntity1)).thenReturn(gameData1);
        when(gameEntityToGameDataConverter.convert(gameEntity2)).thenReturn(gameData2);

        //when
        List<GameData> games = gameService.getGames();

        //then
        Assertions.assertThat(games.get(0).getName()).isEqualTo("test1");
        Assertions.assertThat(games.get(0).getType()).isEqualTo("test2");
        Assertions.assertThat(games.get(0).getDistributionPath()).isEqualTo(DistributionPath.KEY);
        Assertions.assertThat(games.get(1).getName()).isEqualTo("test4");
        Assertions.assertThat(games.get(1).getType()).isEqualTo("test5");
        Assertions.assertThat(games.get(1).getDistributionPath()).isEqualTo(DistributionPath.CD);
    }

    @Test
    public void shouldGetGamesByParameters() {
        //given
        GameEntity gameEntity1 = new GameEntity();
        setEntityData(gameEntity1, "test1", "test2", DistributionPath.KEY);
        GameData gameData1 = new GameData();
        setGameData(gameData1, "test1", "test2", DistributionPath.KEY);
        when(gameRepository.findGameEntityByNameOrProducerEntity_ProducerName(anyString(), anyString())).thenReturn(Arrays.asList(gameEntity1));
        when(gameEntityToGameDataConverter.convert(gameEntity1)).thenReturn(gameData1);

        //when
        List<GameData> games = gameService.getGames("test1", "test2");

        //then
        Assertions.assertThat(games.get(0).getName()).isEqualTo("test1");
        Assertions.assertThat(games.get(0).getType()).isEqualTo("test2");
        Assertions.assertThat(games.get(0).getDistributionPath()).isEqualTo(DistributionPath.KEY);
    }

    private void setGameData(GameData gameData1, String test1, String test2, DistributionPath key) {
        gameData1.setName(test1);
        gameData1.setType(test2);
        gameData1.setDistributionPath(key);
    }

    private void setEntityData(GameEntity gameEntity1, String test1, String test2, DistributionPath key) {
        gameEntity1.setName(test1);
        gameEntity1.setType(test2);
        gameEntity1.setProducerEntity(new ProducerEntity());
        gameEntity1.setDistributionPath(key);
    }
}