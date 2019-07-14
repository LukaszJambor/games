package com.example2.demo.services;

import com.example2.demo.dao.PaymentHistoryRepository;
import com.example2.demo.dao.UserRepository;
import com.example2.demo.model.PaymentEntity;
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