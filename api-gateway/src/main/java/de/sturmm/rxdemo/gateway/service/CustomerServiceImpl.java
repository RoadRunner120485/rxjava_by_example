package de.sturmm.rxdemo.gateway.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.sturmm.rxdemo.gateway.model.Customer;
import de.sturmm.rxdemo.gateway.repository.CustomerRepository;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by sturmm on 09.06.17.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Cache<String, Customer> cache;

    private final CustomerRepository repository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
        cache = CacheBuilder.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(20, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public Single<Customer> findCustomer(String id) {
        // there will be no backend call as long as there is no subscriber
        final Single<Customer> uncached = repository.findCustomer(id)
                .doOnSuccess(customer -> cache.put(id, customer));

        // read data from "request-scoped" cache
        return Maybe.fromCallable(() -> cache.getIfPresent(id))
                // when no entry was found, go on with backend call
                .switchIfEmpty(uncached.toMaybe())
                .toSingle();
    }
}
