package com.example2.demo.model;

import com.example2.demo.model.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;

/**
 * Created by USER on 01.06.2019.
 */

@Setter
@Getter
@Embeddable
public class PriceEntity {

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
