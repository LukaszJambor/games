package com.example2.demo.model;

import com.example2.demo.model.enums.ActivationType;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String login;

    private String password;

    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_key")
    private List<RoleEntity> roles;

    @Lazy
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_key")
    private List<UserTokenEntity> userTokens;

    @Lazy
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_key")
    private WalletEntity wallet;

    public String getLastHash(ActivationType activationType) {
            return getTokensWithSelectedType(activationType).stream()
                    .reduce((first, second) -> second)
                    .map(UserTokenEntity::getHash)
                    .orElse(Strings.EMPTY);
        }

    public boolean isActivationHashAvailable(ActivationType activationType) {
        return !CollectionUtils.isEmpty(getTokensWithSelectedType(activationType));
    }

    private List<UserTokenEntity> getTokensWithSelectedType(ActivationType activationType) {
        return userTokens.stream()
                .filter(entity -> activationType.equals(entity.getActivationType()))
                .collect(Collectors.toList());
    }
}