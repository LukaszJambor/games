package com.example2.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    private String producer;

    private DistributionPath distributionPath;

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

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public DistributionPath getDistributionPath() {
        return distributionPath;
    }

    public void setDistributionPath(DistributionPath distributionPath) {
        this.distributionPath = distributionPath;
    }
}
