package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by USER on 02.06.2019.
 */

@Setter
@Getter
@Entity
public class AddressEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String postalCode;
    private String city;
    private String street;
    private String streetNumber;
}
