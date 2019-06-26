package com.example2.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class UserEntity {

    @GeneratedValue
    @Id
    private Long id;

    private String login;

    private String password;

    private boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="user_key")
    private List<RoleEntity> roleEntityList;

    @Lazy
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="user_key")
    private List<UserTokenEntity> userTokenEntityList;
}