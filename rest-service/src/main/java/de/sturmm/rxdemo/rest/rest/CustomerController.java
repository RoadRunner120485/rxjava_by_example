package de.sturmm.rxdemo.rest.rest;

import de.sturmm.rxdemo.rest.model.carpool.Car;
import de.sturmm.rxdemo.rest.model.customer.Customer;
import de.sturmm.rxdemo.rest.repository.CarRepository;
import de.sturmm.rxdemo.rest.repository.CustomerRepository;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by sturmm on 06.06.17.
 */
@RestController
@RequestMapping("/customers")
@ExposesResourceFor(Customer.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    @GetMapping(path = "/{cid}")
    public Single<Resource<Customer>> loadCustomerById(@PathVariable("cid") String customerId) {
        return Single.fromCallable(() -> customerRepository.findOne(customerId))
                .map(CustomerController::toCustomerResource);
    }

    static Resource<Customer> toCustomerResource(Customer customer) {
        final Resource<Customer> result = new Resource<>(customer);
        result.add(linkTo(CustomerController.class).slash(customer.getId()).withSelfRel());
        result.add(linkTo(methodOn(CustomerController.class).loadCarsByCustomerId(customer.getId())).withRel("cars"));
        return result;
    }

    @GetMapping(path = "/{cid}/cars")
    public Single<Resources<Resource<Car>>> loadCarsByCustomerId(@PathVariable("cid") String customerId) {
        return Single.fromCallable(() -> carRepository.findByCustomerId(customerId))
                .map(cars -> {
                    final List<Resource<Car>> carResources = cars.stream()
                            .map(CarController::toCarResource)
                            .collect(toList());

                    final Resources<Resource<Car>> result = new Resources<>(carResources);
                    result.add(linkTo(methodOn(CustomerController.class).loadCarsByCustomerId(customerId)).withSelfRel());
                    return result;
                });
    }

    @PutMapping(path = "/{cid}")
    public Single<Resource<Customer>> updateCustomer(@PathVariable("cid") String customerId, @RequestBody Customer customer) {
        return Single.fromCallable(() -> customerRepository.findOne(customerId))
                .map(fromDb -> Customer.builder().id(fromDb.getId()).name(customer.getName()).build())
                .map(customerRepository::save)
                .map(CustomerController::toCustomerResource);
    }

}
