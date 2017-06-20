package de.sturmm.rxdemo.gateway.repository.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by sturmm on 08.06.17.
 */
@Data
@Setter(AccessLevel.PRIVATE)
public class CustomerResource {

    private String id;
    private PersonNameResource name;

}
