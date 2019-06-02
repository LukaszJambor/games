package com.example2.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by USER on 31.05.2019.
 */
@Embeddable
public class ProducerEntity {

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
}