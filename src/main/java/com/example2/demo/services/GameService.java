package com.example2.demo.services;

import com.example2.demo.dao.*;
import com.example2.demo.dao.specifications.GameSpecification;
import com.example2.demo.exception.DuplicatedLendException;
import com.example2.demo.exception.NotEnoughCopiesException;
import com.example2.demo.exception.NotEnoughMoneyException;
import com.example2.demo.model.*;
import com.example2.demo.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private WalletRepository walletRepository;
    private PaymentHistoryRepository paymentHistoryRepository;

    @Value("${lend.price}")
    private BigDecimal lendValue;

    public GameService(GameRepository gameRepository, GameSpecification gameSpecification,
                       UserRepository userRepository, LendRepository lendRepository, WalletRepository walletRepository,
                       PaymentHistoryRepository paymentHistoryRepository) {
        this.gameRepository = gameRepository;
        this.gameSpecification = gameSpecification;
        this.userRepository = userRepository;
        this.lendRepository = lendRepository;
        this.walletRepository = walletRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
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
        if(lendValue.compareTo(userEntity.getWallet().getMoney())>=0){
            throw new NotEnoughMoneyException("not enough money on account");
        }
        Optional<GameEntity> gameEntityById = gameRepository.findById(gameId);
        if (gameEntityById.isPresent() && gameEntityById.get().getQuantity() > 0) {
            GameEntity gameEntity = gameEntityById.get();
            createLend(userEntity, gameEntity);
            createPaymentHistoryRecord(userEntity, gameEntity);
            updateQuantity(gameEntity);
            updateWallet(userEntity);
        } else {
            throw new NotEnoughCopiesException("impossible to lend unavailable game");
        }
    }

    private void createPaymentHistoryRecord(UserEntity userEntity, GameEntity gameEntity) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setGame(gameEntity);
        paymentEntity.setUser(userEntity);
        paymentEntity.setCost(lendValue);
        paymentHistoryRepository.save(paymentEntity);
    }

    private void updateWallet(UserEntity userEntity) {
        WalletEntity wallet = userEntity.getWallet();
        wallet.setMoney(wallet.getMoney().subtract(lendValue));
        walletRepository.save(wallet);
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