package com.example2.demo.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by USER on 31.05.2019.
 */
@Entity
public class ProducerEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String producerName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AddressEntity> addressEntity;

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public List<AddressEntity> getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(List<AddressEntity> addressEntity) {
        this.addressEntity = addressEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}