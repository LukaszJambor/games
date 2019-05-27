package com.example2.demo.entity;

import com.example2.demo.enums.DistributionPath;

import javax.persistence.Column;
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

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private String producer;

    @Column
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
