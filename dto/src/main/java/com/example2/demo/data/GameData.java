package com.example2.demo.data;

import com.example2.demo.model.enums.Currency;
import com.example2.demo.model.enums.DistributionPath;
import com.example2.demo.validators.DistributionPathValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.math.BigDecimal;
import java.util.List;

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

    private Link link;

    private List<CommentData> commentDataList;
}