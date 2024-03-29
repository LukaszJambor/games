package com.example2.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Lazy;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class PaymentEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @CreationTimestamp
    private LocalDateTime paymentTime;

    private BigDecimal cost;

    @Lazy
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Lazy
    @OneToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;
}