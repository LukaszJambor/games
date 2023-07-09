package com.example2.demo.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

/**
 * Created by USER on 31.05.2019.
 */

@Setter
@Getter
@Entity
public class ProducerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String producerName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "producer_key")
    private List<AddressEntity> addresses;
}