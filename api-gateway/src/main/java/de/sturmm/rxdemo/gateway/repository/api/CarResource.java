package de.sturmm.rxdemo.gateway.repository.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * Created by sturmm on 06.06.17.
 */
@Data
@Setter(AccessLevel.PRIVATE)
public class CarResource {

    private String id;

    private String brand;
    private String model;

}
