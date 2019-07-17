package model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class WalletEntity {

    @GeneratedValue
    @Id
    private Long id;

    private BigDecimal money;

    @Lazy
    @OneToOne(mappedBy = "wallet")
    private UserEntity user;
}
