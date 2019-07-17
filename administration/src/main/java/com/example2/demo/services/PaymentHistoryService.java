package com.example2.demo.services;

import dao.PaymentHistoryRepository;
import dao.UserRepository;
import model.PaymentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentHistoryService {

    private PaymentHistoryRepository paymentHistoryRepository;
    private UserRepository userRepository;

    public PaymentHistoryService(PaymentHistoryRepository paymentHistoryRepository, UserRepository userRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.userRepository = userRepository;
    }

    public List<PaymentEntity> getHistory(Long userId) {
        return paymentHistoryRepository.findAllByUser_Id(userId);
    }
}