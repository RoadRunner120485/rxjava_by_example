package de.sturmm.rxdemo.rest.service;

import de.sturmm.rxdemo.rest.model.customer.Customer;
import de.sturmm.rxdemo.rest.model.customer.PersonName;
import io.reactivex.Single;

/**
 * Created by sturmm on 17.06.17.
 */
public interface CustomerService {

    Single<Customer> updateCustomerName(String customerId, PersonName newName);

}
