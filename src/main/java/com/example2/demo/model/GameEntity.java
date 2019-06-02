package com.example2.demo.model;

import javax.persistence.*;

/**
 * Created by USER on 25.05.2019.
 */

@Entity
public class GameEntity {

    @GeneratedValue
    @Id
    private Long id;

    private String name;

    private String type;

    @Enumerated(EnumType.STRING)
    private DistributionPath distributionPath;

    @Embedded
    private ProducerEntity producerEntity;

    @OneToOne(cascade = CascadeType.ALL)
    private PriceEntity priceEntity;

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

    public DistributionPath getDistributionPath() {
        return distributionPath;
    }

    public void setDistributionPath(DistributionPath distributionPath) {
        this.distributionPath = distributionPath;
    }

    public ProducerEntity getProducerEntity() {
        return producerEntity;
    }

    public void setProducerEntity(ProducerEntity producerEntity) {
        this.producerEntity = producerEntity;
    }

    public PriceEntity getPriceEntity() {
        return priceEntity;
    }

    public void setPriceEntity(PriceEntity priceEntity) {
        this.priceEntity = priceEntity;
    }
}
