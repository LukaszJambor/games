package com.example2.demo.data;

import com.example2.demo.model.enums.Currency;
import com.example2.demo.model.enums.DistributionPath;
import com.example2.demo.validators.DistributionPathValidator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * Created by USER on 25.05.2019.
 */

@Setter
@Getter
public class GameData {


    private Long id;

    @NotBlank(message = "name message")
    private String name;

    @NotBlank(message = "type message")
    private String type;

    @NotBlank(message = "producer name message")
    private String producerName;

    @DistributionPathValidator
    private DistributionPath distributionPath;

    @Positive
    @NotNull(message = "price message")
    private BigDecimal price;

    @NotNull(message = "currency message")
    private Currency currency;

    @PositiveOrZero
    @NotNull(message = "quantity message")
    private int quantity;

    @NotNull(message = "barcode required")
    private Long barcode;
}