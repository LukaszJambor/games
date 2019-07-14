package com.example2.demo.data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class PaymentData {

    private LocalDateTime paymentTime;
    private BigDecimal cost;
    private String gameName;
}
