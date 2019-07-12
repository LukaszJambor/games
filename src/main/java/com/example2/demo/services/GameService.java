package com.example2.demo.services;

import com.example2.demo.dao.GameRepository;
import com.example2.demo.dao.LendRepository;
import com.example2.demo.dao.UserRepository;
import com.example2.demo.dao.specifications.GameSpecification;
import com.example2.demo.exception.DuplicatedLendException;
import com.example2.demo.exception.NotEnoughCopiesException;
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

/**
 * Created by USER on 25.05.2019.
 */

@Component
public class GameService {

    private GameRepository gameRepository;
    private GameSpecification gameSpecification;
    private UserRepository userRepository;
    private LendRepository lendRepository;

    public GameService(GameRepository gameRepository, GameSpecification gameSpecification,
                       UserRepository userRepository, LendRepository lendRepository) {
        this.gameRepository = gameRepository;
        this.gameSpecification = gameSpecification;
        this.userRepository = userRepository;
        this.lendRepository = lendRepository;
    }

    public void addGame(GameEntity gameEntity) {
        gameRepository.save(gameEntity);
    }

    public List<GameEntity> getGames() {
        return gameRepository.findAll();
    }

    public List<GameEntity> getGames(String name, String producer) {
        return gameRepository.findAll(gameSpecification.findGameEntityByNameOrProducerName(name, producer));
    }

    public List<LendEntity> getUserGamePanel(Long userId) {
        UserEntity userEntity = userRepository.getOne(userId);
        if (SecurityUtil.getUserName().equals(userEntity.getLogin())) {
            return lendRepository.findByUserId(userId);
        }
        throw new AccessDeniedException("request userId is different than in session");
    }

    @Transactional
    public void createLend(Long userId, Long gameId) {
        UserEntity userEntity = userRepository.getOne(userId);
        if (!SecurityUtil.getUserName().equals(userEntity.getLogin())) {
            throw new AccessDeniedException("request userId is different than in session");
        }
        Optional<LendEntity> lendEntity = lendRepository.findByUserIdAndGameIdAndLendEndDateIsNull(userId, gameId);
        if (lendEntity.isPresent()) {
            throw new DuplicatedLendException("impossible to lend same game twice");
        }
        Optional<GameEntity> gameEntityById = gameRepository.findById(gameId);
        if (gameEntityById.isPresent() && gameEntityById.get().getQuantity() > 0) {
            GameEntity gameEntity = gameEntityById.get();
            createLend(userEntity, gameEntity);
            updateQuantity(gameEntity);
        } else {
            throw new NotEnoughCopiesException("impossible to lend unavailable game");
        }
    }

    @Transactional
    public void createReturn(Long userId, Long gameId) {
        UserEntity userEntity = userRepository.getOne(userId);
        if (!SecurityUtil.getUserName().equals(userEntity.getLogin())) {
            throw new AccessDeniedException("request userId is different than in session");
        }
        Optional<LendEntity> lendEntity = lendRepository.findByUserIdAndGameIdAndLendEndDateIsNull(userId, gameId);
        if(lendEntity.isPresent()){
            createReturn(lendEntity.get());
            updateStockAfterReturn(lendEntity.get());
        }
    }


    private void updateQuantity(GameEntity gameEntity) {
        gameEntity.setQuantity(gameEntity.getQuantity() - 1);
        gameRepository.save(gameEntity);
    }

    private void createLend(UserEntity userEntity, GameEntity gameEntity) {
        LendEntity lendEntity = new LendEntity();
        lendEntity.setGame(gameEntity);
        lendEntity.setUser(userEntity);
        lendRepository.save(lendEntity);
    }

    private void updateStockAfterReturn(LendEntity lendEntity) {
        GameEntity gameEntity = lendEntity.getGame();
        gameEntity.setQuantity(gameEntity.getQuantity() + 1);
        gameRepository.save(gameEntity);
    }

    private void createReturn(LendEntity lendEntity) {
        lendEntity.setLendEndDate(LocalDateTime.now());
        lendRepository.save(lendEntity);
    }
}