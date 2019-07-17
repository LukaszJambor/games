package model;

import model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class RoleEntity {

    @GeneratedValue
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;
}
