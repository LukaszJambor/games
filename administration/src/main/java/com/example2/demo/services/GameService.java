package com.example2.demo.services;

import com.example2.demo.dao.*;
import com.example2.demo.dao.specifications.GameSpecification;
import com.example2.demo.exception.DuplicatedLendException;
import com.example2.demo.exception.LendNotFoundException;
import com.example2.demo.exception.NotEnoughCopiesException;
import com.example2.demo.exception.NotEnoughMoneyException;
import com.example2.demo.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private CommentRepository commentRepository;

    @Value("${lend.price}")
    private BigDecimal lendValue;

    public GameService(GameRepository gameRepository, GameSpecification gameSpecification,
                       UserRepository userRepository, LendRepository lendRepository, WalletRepository walletRepository,
                       PaymentHistoryRepository paymentHistoryRepository, CommentRepository commentRepository) {
        this.gameRepository = gameRepository;
        this.gameSpecification = gameSpecification;
        this.userRepository = userRepository;
        this.lendRepository = lendRepository;
        this.walletRepository = walletRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.commentRepository = commentRepository;
    }

    public GameEntity addGame(GameEntity gameEntity) {
        return gameRepository.save(gameEntity);
    }

    public List<GameEntity> getGames() {
        return gameRepository.findAll();
    }

    public List<GameEntity> getGames(String name, String producer) {
        return gameRepository.findAll(gameSpecification.findGameEntityByNameOrProducerName(name, producer));
    }

    public List<LendEntity> getUserGamePanel(Long userId) {
        UserEntity userEntity = userRepository.getOne(userId);
        return lendRepository.findByUserId(userId);
    }

    @Transactional
    public LendEntity createLend(Long userId, Long gameId) {
        UserEntity userEntity = userRepository.getOne(userId);
        Optional<LendEntity> lendEntity = lendRepository.findByUserIdAndGameIdAndLendEndDateIsNull(userId, gameId);
        if (lendEntity.isPresent()) {
            throw new DuplicatedLendException("impossible to lend same game twice");
        }
        if (lendValue.compareTo(userEntity.getWallet().getMoney()) >= 0) {
            throw new NotEnoughMoneyException("not enough money on account");
        }
        Optional<GameEntity> gameEntityById = gameRepository.findById(gameId);
        if (gameEntityById.isPresent() && gameEntityById.get().getQuantity() > 0) {
            GameEntity gameEntity = gameEntityById.get();
            LendEntity lend = createLend(userEntity, gameEntity);
            createPaymentHistoryRecord(userEntity, gameEntity);
            updateQuantity(gameEntity);
            updateWallet(userEntity);
            return lend;
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
    public LendEntity createReturn(Long userId, Long gameId) {
        Optional<LendEntity> lendEntity = lendRepository.findByUserIdAndGameIdAndLendEndDateIsNull(userId, gameId);
        if (lendEntity.isPresent()) {
            LendEntity aReturn = createReturn(lendEntity.get());
            updateStockAfterReturn(lendEntity.get());
            createBaseComment();
            return aReturn;
        } else {
            throw new LendNotFoundException("lend not found");
        }
    }

    private void createBaseComment() {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUuid(UUID.randomUUID().toString());
        commentRepository.save(commentEntity);
    }

    @Transactional
    public CommentEntity createComment(CommentEntity commentEntity) {
        Optional<CommentEntity> commentEntityByUuid = commentRepository.findCommentEntityByUuid(commentEntity.getUuid());
        if (commentEntityByUuid.isPresent()) {
            CommentEntity commentEntityInternal = commentEntityByUuid.get();
            commentEntityInternal.setComment(commentEntity.getComment());
            commentEntityInternal.setGameKey(commentEntity.getGameKey());
            return commentRepository.save(commentEntityInternal);
        }
        return null;
    }

    private void updateQuantity(GameEntity gameEntity) {
        gameEntity.setQuantity(gameEntity.getQuantity() - 1);
        gameRepository.save(gameEntity);
    }

    private LendEntity createLend(UserEntity userEntity, GameEntity gameEntity) {
        LendEntity lendEntity = new LendEntity();
        lendEntity.setGame(gameEntity);
        lendEntity.setUser(userEntity);
        return lendRepository.save(lendEntity);
    }

    private void updateStockAfterReturn(LendEntity lendEntity) {
        GameEntity gameEntity = lendEntity.getGame();
        gameEntity.setQuantity(gameEntity.getQuantity() + 1);
        gameRepository.save(gameEntity);
    }

    private LendEntity createReturn(LendEntity lendEntity) {
        lendEntity.setLendEndDate(LocalDateTime.now());
        return lendRepository.save(lendEntity);
    }
}