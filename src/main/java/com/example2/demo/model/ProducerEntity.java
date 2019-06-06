package com.example2.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by USER on 31.05.2019.
 */

@Setter
@Getter
@Entity
public class ProducerEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String producerName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AddressEntity> addressEntity;
}