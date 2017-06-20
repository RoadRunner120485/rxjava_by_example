package de.sturmm.rxdemo.rest.service;

import de.sturmm.rxdemo.rest.model.customer.Customer;
import de.sturmm.rxdemo.rest.model.customer.PersonName;
import de.sturmm.rxdemo.rest.repository.CustomerRepository;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by sturmm on 17.06.17.
 */
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Transactional
    public Single<Customer> updateCustomerName(String customerId, PersonName newName) {
        // Single.fromCallable(() -> repository.findOne(customerId))
        return Single.just(customerId)
                .map(repository::findOne)
                .map(customer -> customer.withName(newName))
                .map(repository::updateOnTx);
    }
}
