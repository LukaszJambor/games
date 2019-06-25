package com.example2.demo.model;

import com.example2.demo.model.enums.ActivationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class ActivationEntity {

    @GeneratedValue
    @Id
    private Long id;

    private String hash;

    @Enumerated(EnumType.STRING)
    private ActivationType activationType;

    private LocalDateTime activationTimestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_key")
    private UserEntity userEntity;
}