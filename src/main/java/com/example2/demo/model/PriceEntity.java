package com.example2.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
