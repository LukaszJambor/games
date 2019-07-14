package com.example2.demo.services;

import com.example2.demo.dao.PaymentHistoryRepository;
import com.example2.demo.dao.UserRepository;
import com.example2.demo.model.PaymentEntity;
import com.example2.demo.model.UserEntity;
import com.example2.demo.util.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentHistoryService {

    PaymentHistoryRepository paymentHistoryRepository;
    UserRepository userRepository;

    public PaymentHistoryService(PaymentHistoryRepository paymentHistoryRepository, UserRepository userRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.userRepository = userRepository;
    }

    public List<PaymentEntity> getHistory(Long userId) {
        UserEntity userEntity = userRepository.getOne(userId);
        if (!SecurityUtil.getUserName().equals(userEntity.getLogin())) {
            throw new AccessDeniedException("request userId is different than in session");
        }
        return paymentHistoryRepository.findAllByUser_Id(userId);
    }
}