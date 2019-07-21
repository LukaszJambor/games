package com.example2.demo.cronjob.lendAdditionalCosts;

import com.example2.demo.dao.LendRepository;
import com.example2.demo.dao.PaymentHistoryRepository;
import com.example2.demo.dao.WalletRepository;
import com.example2.demo.model.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class LendAdditionalCostsJob implements Job {

    private LendRepository lendRepository;
    private WalletRepository walletRepository;
    private PaymentHistoryRepository paymentHistoryRepository;

    @Value("${lend.extra.payment.per.hour}")
    private BigDecimal overDueCharge;

    public LendAdditionalCostsJob(LendRepository lendRepository, WalletRepository walletRepository, PaymentHistoryRepository paymentHistoryRepository) {
        this.lendRepository = lendRepository;
        this.walletRepository = walletRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
    }

    @Transactional
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<LendEntity> overdueLends = lendRepository.findByLendStartDateBeforeAndLendEndDateIsNull(LocalDateTime.now().minusDays(2L));
        overdueLends.stream()
                .forEach(this::chargeAdditionCost);

    }

    private void chargeAdditionCost(LendEntity overdueLend) {
        createPaymentHistoryRecord(overdueLend.getUser(), overdueLend.getGame());
        WalletEntity wallet = overdueLend.getUser().getWallet();
        wallet.setMoney(wallet.getMoney().subtract(overDueCharge));
        walletRepository.save(wallet);
    }

    private void createPaymentHistoryRecord(UserEntity userEntity, GameEntity gameEntity) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setGame(gameEntity);
        paymentEntity.setUser(userEntity);
        paymentEntity.setCost(overDueCharge);
        paymentEntity.setPaymentTime(LocalDateTime.now());
        paymentHistoryRepository.save(paymentEntity);
    }
}