package com.example2.demo.data;

import com.example2.demo.model.Currency;
import com.example2.demo.model.DistributionPath;

import java.math.BigDecimal;

/**
 * Created by USER on 25.05.2019.
 */
public class GameData {

    private Long id;
    private String name;
    private String type;
    private String producerName;
    private DistributionPath distributionPath;
    private BigDecimal price;
    private Currency currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public DistributionPath getDistributionPath() {
        return distributionPath;
    }

    public void setDistributionPath(DistributionPath distributionPath) {
        this.distributionPath = distributionPath;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
