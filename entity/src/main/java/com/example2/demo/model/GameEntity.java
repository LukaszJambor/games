package com.example2.demo.model;

import com.example2.demo.model.enums.DistributionPath;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by USER on 25.05.2019.
 */

@Setter
@Getter
@Entity
public class GameEntity {

    @GeneratedValue
    @Id
    private Long id;

    private String name;

    private String type;

    @Enumerated(EnumType.STRING)
    private DistributionPath distributionPath;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="producer_key")
    private ProducerEntity producer;

    @Embedded
    private PriceEntity price;

    private Integer quantity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_key")
    private List<CommentEntity> comments;
}
