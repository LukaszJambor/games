package com.example2.demo.services;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.converters.LendEntityToLendDataMapper;
import com.example2.demo.dao.GameRepository;
import com.example2.demo.dao.LendRepository;
import com.example2.demo.dao.UserRepository;
import com.example2.demo.dao.specifications.GameSpecification;
import com.example2.demo.data.GameData;
import com.example2.demo.data.LendData;
import com.example2.demo.model.GameEntity;
import com.example2.demo.model.LendEntity;
import com.example2.demo.model.UserEntity;
import com.example2.demo.util.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by USER on 25.05.2019.
 */

@Component
public class GameService {

    private GameEntityGameDataMapper gameEntityGameDataMapper;
    private GameRepository gameRepository;
    private GameSpecification gameSpecification;
    private UserRepository userRepository;
    private LendRepository lendRepository;
    private LendEntityToLendDataMapper lendEntityToLendDataMapper;

    public GameService(GameEntityGameDataMapper gameEntityGameDataMapper,
                       GameRepository gameRepository, GameSpecification gameSpecification,
                       UserRepository userRepository, LendRepository lendRepository,
                       LendEntityToLendDataMapper lendEntityToLendDataMapper) {
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
        this.gameRepository = gameRepository;
        this.gameSpecification = gameSpecification;
        this.userRepository = userRepository;
        this.lendRepository = lendRepository;
        this.lendEntityToLendDataMapper = lendEntityToLendDataMapper;
    }

    public void addGame(GameData gameData) {
        GameEntity gameEntity = gameEntityGameDataMapper.toEntity(gameData);
        gameRepository.save(gameEntity);
    }

    public List<GameData> getGames() {
        List<GameEntity> all = gameRepository.findAll();
        return convertToData(all);
    }

    public List<GameData> getGames(String name, String producer) {
        List<GameEntity> gameByNameOrProducer = gameRepository.findAll(gameSpecification.findGameEntityByNameOrProducerName(name, producer));
        return convertToData(gameByNameOrProducer);
    }

    private List<GameData> convertToData(List<GameEntity> all) {
        return all.stream()
                .map(game -> gameEntityGameDataMapper.toDto(game))
                .collect(Collectors.toList());
    }

    public List<LendData> getUserGamePanel(Long userId) {
        UserEntity userEntity = userRepository.getOne(userId);
        if (SecurityUtil.getUserName().equals(userEntity.getLogin())) {
            List<LendEntity> lendEntityByUserId = lendRepository.findByUserEntityId(userId);
            return lendEntityByUserId.stream()
                    .map(lendEntity -> lendEntityToLendDataMapper.toDto(lendEntity))
                    .collect(Collectors.toList());
        }
        throw new AccessDeniedException("request userId is different than in session");
    }

    @Transactional
    public void createLend(Long userId, Long gameId) {
        UserEntity userEntity = userRepository.getOne(userId);
        if (!SecurityUtil.getUserName().equals(userEntity.getLogin())) {
            throw new AccessDeniedException("request userId is different than in session");
        }
        Optional<GameEntity> gameEntityById = gameRepository.findById(gameId);
        if (gameEntityById.isPresent() && gameEntityById.get().getQuantity() > 0) {
            GameEntity gameEntity = gameEntityById.get();
            createLend(userEntity, gameEntity);
            updateQuantity(gameEntity);
        } else {
            //throw something
        }
    }

    private void updateQuantity(GameEntity gameEntity) {
        gameEntity.setQuantity(gameEntity.getQuantity()-1);
        gameRepository.save(gameEntity);
    }

    private void createLend(UserEntity userEntity, GameEntity gameEntity) {
        LendEntity lendEntity = new LendEntity();
        lendEntity.setGameEntity(gameEntity);
        lendEntity.setUserEntity(userEntity);
        lendRepository.save(lendEntity);
    }

    @Transactional
    public void createReturn(Long userId, Long gameId) {
        UserEntity userEntity = userRepository.getOne(userId);
        if (!SecurityUtil.getUserName().equals(userEntity.getLogin())) {
            throw new AccessDeniedException("request userId is different than in session");
        }
        LendEntity lendEntity = lendRepository.findByUserEntityIdAndGameEntityIdAndLendEndDateIsNull(userId, gameId);
        createReturn(lendEntity);
        updateStockAfterReturn(lendEntity);

    }

    private void updateStockAfterReturn(LendEntity lendEntity) {
        GameEntity gameEntity = lendEntity.getGameEntity();
        gameEntity.setQuantity(gameEntity.getQuantity()+1);
        gameRepository.save(gameEntity);
    }

    private void createReturn(LendEntity lendEntity) {
        lendEntity.setLendEndDate(LocalDateTime.now());
        lendRepository.save(lendEntity);
    }
}