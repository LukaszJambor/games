package com.example2.demo.data;

import model.enums.Currency;
import model.enums.DistributionPath;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by USER on 25.05.2019.
 */

@Setter
@Getter
public class GameData {

    private Long id;
    private String name;
    private String type;
    private String producerName;
    private DistributionPath distributionPath;
    private BigDecimal price;
    private Currency currency;
    private int quantity;
}
