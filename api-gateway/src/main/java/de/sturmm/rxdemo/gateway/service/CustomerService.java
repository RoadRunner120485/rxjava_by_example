package de.sturmm.rxdemo.gateway.service;

import de.sturmm.rxdemo.gateway.model.Customer;
import io.reactivex.Single;

/**
 * Created by sturmm on 09.06.17.
 */
public interface CustomerService {

    Single<Customer> findCustomer(String id);

}
