package com.example2.demo.model;

import com.example2.demo.model.enums.ActivationType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class UserTokenEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String hash;

    @Enumerated(EnumType.STRING)
    private ActivationType activationType;

    private LocalDateTime activationTimestamp;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @ManyToOne
    @JoinColumn(name="user_key")
    private UserEntity user;
}