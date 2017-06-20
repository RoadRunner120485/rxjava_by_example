package de.sturmm.rxdemo.rest.controller;

import de.sturmm.rxdemo.rest.model.customer.Customer;
import de.sturmm.rxdemo.rest.model.customer.PersonName;
import de.sturmm.rxdemo.rest.service.CustomerService;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sturmm on 17.06.17.
 */
@RestController
@RequestMapping("/customers")
@ExposesResourceFor(Customer.class)
@RequiredArgsConstructor
public class CustomerResourceController {

    private final EntityLinks links;
    private final CustomerService customerService;

    @RequestMapping(path = "/{cid}/updateName", method = RequestMethod.POST)
    public Single<Link> updateName(@PathVariable("cid") String customerId, @RequestBody PersonName name) {
        return customerService.updateCustomerName(customerId, name)
                .map(customer -> links.linkFor(Customer.class).slash(customerId).withSelfRel());
    }

}
