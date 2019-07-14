package com.example2.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class PaymentEntity {

    @GeneratedValue
    @Id
    private Long id;

    @CreationTimestamp
    private LocalDateTime paymentTime;

    private BigDecimal cost;

    @Lazy
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Lazy
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private GameEntity game;
}