package com.example2.demo.services;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.dao.GameRepository;
import com.example2.demo.dao.specifications.GameSpecification;
import com.example2.demo.data.GameData;
import com.example2.demo.model.enums.DistributionPath;
import com.example2.demo.model.GameEntity;
import com.example2.demo.model.ProducerEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by USER on 28.05.2019.
 */
public class GameServiceTest {

    @Mock
    private GameEntityGameDataMapper gameEntityToGameDataConverter;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameSpecification gameSpecification;

    @Mock
    private Specification<GameEntity> specificationEntityMock;

    @InjectMocks
    private GameService gameService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddGame() {
        //given
        when(gameEntityToGameDataConverter.toEntity(any())).thenReturn(new GameEntity());

        //when
        gameService.addGame(new GameEntity());

        //then
        verify(gameRepository, times(1)).save(any());
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
        when(gameEntityToGameDataConverter.toDto(gameEntity1)).thenReturn(gameData1);
        when(gameEntityToGameDataConverter.toDto(gameEntity2)).thenReturn(gameData2);

        //when
        List<GameEntity> games = gameService.getGames();

        //then
        assertThat(games.get(0).getName()).isEqualTo("test1");
        assertThat(games.get(0).getType()).isEqualTo("test2");
        assertThat(games.get(0).getDistributionPath()).isEqualTo(DistributionPath.KEY);
        assertThat(games.get(1).getName()).isEqualTo("test4");
        assertThat(games.get(1).getType()).isEqualTo("test5");
        assertThat(games.get(1).getDistributionPath()).isEqualTo(DistributionPath.CD);
    }

    @Test
    public void shouldGetGamesByParameters() {
        //given
        GameEntity gameEntity1 = new GameEntity();
        setEntityData(gameEntity1, "test1", "test2", DistributionPath.KEY);
        GameData gameData1 = new GameData();
        setGameData(gameData1, "test1", "test2", DistributionPath.KEY);
        when(gameSpecification.findGameEntityByNameOrProducerName(anyString(), anyString())).thenReturn(specificationEntityMock);
        when(gameRepository.findAll((Specification) any())).thenReturn(Collections.singletonList(gameEntity1));
        when(gameEntityToGameDataConverter.toDto(gameEntity1)).thenReturn(gameData1);

        //when
        List<GameEntity> games = gameService.getGames("test1", "test2");

        //then
        assertThat(games.get(0).getName()).isEqualTo("test1");
        assertThat(games.get(0).getType()).isEqualTo("test2");
        assertThat(games.get(0).getDistributionPath()).isEqualTo(DistributionPath.KEY);
    }

    private void setGameData(GameData gameData1, String test1, String test2, DistributionPath key) {
        gameData1.setName(test1);
        gameData1.setType(test2);
        gameData1.setDistributionPath(key);
    }

    private void setEntityData(GameEntity gameEntity1, String test1, String test2, DistributionPath key) {
        gameEntity1.setName(test1);
        gameEntity1.setType(test2);
        gameEntity1.setProducer(new ProducerEntity());
        gameEntity1.setDistributionPath(key);
    }
}