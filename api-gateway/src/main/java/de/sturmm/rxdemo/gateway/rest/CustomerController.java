package de.sturmm.rxdemo.gateway.rest;

import de.sturmm.rxdemo.gateway.model.Customer;
import de.sturmm.rxdemo.gateway.repository.CustomerRepository;
import de.sturmm.rxdemo.gateway.service.CustomerService;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sturmm on 06.06.17.
 */
@RestController
@RequestMapping("/customers")
@ExposesResourceFor(Customer.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(path = "/{cid}")
    public Single<Resource<Customer>> loadCustomerById(@PathVariable("cid") String customerId) {
        return customerService.findCustomer(customerId)
                .map(customer -> new Resource<>(customer));
    }

}
