package com.example2.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
}
