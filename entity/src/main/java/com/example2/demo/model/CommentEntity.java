package com.example2.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class CommentEntity {

    @GeneratedValue
    @Id
    private Long id;

    private String comment;

    private String uuid;

    @Column(name = "game_key")
    private Long gameKey;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserEntity user;
}
