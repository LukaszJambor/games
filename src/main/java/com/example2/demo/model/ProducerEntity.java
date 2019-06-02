package com.example2.demo.model;

import javax.persistence.Embeddable;

/**
 * Created by USER on 31.05.2019.
 */
@Embeddable
public class ProducerEntity {

    private String producerName;

    private String address;

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
