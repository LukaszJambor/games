package com.example2.demo.controllers;

import com.example2.demo.converters.PaymentEntityToPaymentDataMapper;
import com.example2.demo.data.PaymentData;
import com.example2.demo.services.PaymentHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/restapi/v1")
public class PaymentHistoryApiController {

    private PaymentHistoryService paymentHistoryService;
    private PaymentEntityToPaymentDataMapper paymentEntityToPaymentDataMapper;

    public PaymentHistoryApiController(PaymentHistoryService paymentHistoryService, PaymentEntityToPaymentDataMapper paymentEntityToPaymentDataMapper) {
        this.paymentHistoryService = paymentHistoryService;
        this.paymentEntityToPaymentDataMapper = paymentEntityToPaymentDataMapper;
    }

    @GetMapping(value = "/users/{userId}/histories")
    public ResponseEntity<List<PaymentData>> getHistory(@PathVariable("userId") Long userId) {
        List<PaymentData> paymentsResource = paymentHistoryService.getHistory(userId).stream()
                .map(historyEntry -> paymentEntityToPaymentDataMapper.toDto(historyEntry))
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentsResource);
    }
}