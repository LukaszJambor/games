package com.example2.demo.services;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.dao.*;
import com.example2.demo.dao.specifications.GameSpecification;
import com.example2.demo.data.GameData;
import com.example2.demo.exception.DuplicatedLendException;
import com.example2.demo.exception.LendNotFoundException;
import com.example2.demo.exception.NotEnoughCopiesException;
import com.example2.demo.exception.NotEnoughMoneyException;
import com.example2.demo.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    public static final String BARCODE = "barcode";
    private GameRepository gameRepository;
    private GameSpecification gameSpecification;
    private UserRepository userRepository;
    private LendRepository lendRepository;
    private WalletRepository walletRepository;
    private PaymentHistoryRepository paymentHistoryRepository;
    private CommentRepository commentRepository;
    private GameEntityGameDataMapper gameEntityGameDataMapper;

    @Value("${lend.price}")
    private BigDecimal lendValue;

    public GameService(GameRepository gameRepository, GameSpecification gameSpecification,
                       UserRepository userRepository, LendRepository lendRepository, WalletRepository walletRepository,
                       PaymentHistoryRepository paymentHistoryRepository, CommentRepository commentRepository,
                       GameEntityGameDataMapper gameEntityGameDataMapper) {
        this.gameRepository = gameRepository;
        this.gameSpecification = gameSpecification;
        this.userRepository = userRepository;
        this.lendRepository = lendRepository;
        this.walletRepository = walletRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.commentRepository = commentRepository;
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
    }

    public GameEntity addGame(GameEntity gameEntity) {
        GameEntity gameEntityMatcher = new GameEntity();
        gameEntityMatcher.setBarcode(gameEntity.getBarcode());
        if (gameRepository.exists(Example.of(gameEntityMatcher,
                ExampleMatcher.matching().withMatcher(BARCODE, ExampleMatcher.GenericPropertyMatchers.exact())))) {
            return gameEntityMatcher;
        }
        return gameRepository.save(gameEntity);
    }

    public Page<GameEntity> getGames(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    public Page<GameEntity> getGames(String name, String producer, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(producer)) {
            return getGames(pageable);
        } else {
            return getGamesByNameOrProducer(name, producer, pageable);
        }
    }

    public Page<GameEntity> getGamesByNameOrProducer(String name, String producer, Pageable pageable) {
        return gameRepository.findAll(gameSpecification.findGameEntityByNameOrProducerName(name, producer), pageable);
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

    public Optional<GameData> getGame(long id) {
        Optional<GameEntity> gameEntity = gameRepository.findById(id);
        return gameEntity.map(gameEntityInternal -> gameEntityGameDataMapper.toDto(gameEntityInternal));
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
            createBaseComment(lendEntity.get());
            return aReturn;
        } else {
            throw new LendNotFoundException("lend not found");
        }
    }

    private void createBaseComment(LendEntity lendEntity) {
        Optional<CommentEntity> storedCommentEntity = commentRepository.findCommentEntityByUserIdAndGameKey(
                lendEntity.getUser().getId(), lendEntity.getGame().getId());
        if (storedCommentEntity.isPresent()) {
            CommentEntity commentEntityInternal = storedCommentEntity.get();
            if (StringUtils.isEmpty(commentEntityInternal.getComment())) {
                createNewUuid(commentEntityInternal);
            }
        } else {
            saveNewComment(lendEntity);
        }
    }

    private void createNewUuid(CommentEntity commentEntityInternal) {
        commentEntityInternal.setUuid(UUID.randomUUID().toString());
        commentRepository.save(commentEntityInternal);
    }

    private void saveNewComment(LendEntity lendEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUuid(UUID.randomUUID().toString());
        commentEntity.setGameKey(lendEntity.getGame().getId());
        commentEntity.setUser(lendEntity.getUser());
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

    public Optional<CommentEntity> updateComment(CommentEntity commentEntity, Long commentId, Long userId) {
        Optional<CommentEntity> commentEntityByUserIdAndId = commentRepository.findCommentEntityByUserIdAndCommentId(userId, commentId);
        return commentEntityByUserIdAndId.map(commentEntityResponse -> {
            commentEntityResponse.setComment(commentEntity.getComment());
            return Optional.of(commentRepository.save(commentEntityResponse));
        }).orElse(Optional.empty());
    }
}