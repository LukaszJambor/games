package com.example2.demo.controllers;

import com.example2.demo.converters.PaymentEntityToPaymentDataMapper;
import com.example2.demo.data.PaymentData;
import com.example2.demo.services.PaymentHistoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PaymentHistoryController {

    private PaymentHistoryService paymentHistoryService;
    private PaymentEntityToPaymentDataMapper paymentEntityToPaymentDataMapper;

    public PaymentHistoryController(PaymentHistoryService paymentHistoryService, PaymentEntityToPaymentDataMapper paymentEntityToPaymentDataMapper) {
        this.paymentHistoryService = paymentHistoryService;
        this.paymentEntityToPaymentDataMapper = paymentEntityToPaymentDataMapper;
    }

    @PreAuthorize(value = "authentication.principal.userId == #userId")
    @RequestMapping(value = "/user/{userId}/history", method = RequestMethod.GET)
    public String getHistory(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("payments", getHistory(userId));
        return "history";
    }

    private List<PaymentData> getHistory(Long userId) {
        return paymentHistoryService.getHistory(userId).stream()
                .map(historyEntry -> paymentEntityToPaymentDataMapper.toDto(historyEntry))
                .collect(Collectors.toList());
    }
}
