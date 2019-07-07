package com.example2.demo.model;

import com.google.common.collect.Iterables;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;

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
    @JoinColumn(name = "user_key")
    private List<RoleEntity> roleEntityList;

    @Lazy
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_key")
    private List<UserTokenEntity> userTokenEntityList;

    public String getLastHash() {
        if (CollectionUtils.isEmpty(userTokenEntityList)) {
            return Strings.EMPTY;
        } else {
            return Iterables.getLast(userTokenEntityList).getHash();
        }
    }
}