package com.example2.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class WalletEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private BigDecimal money;

    @Lazy
    @OneToOne(mappedBy = "wallet")
    private UserEntity user;
}
