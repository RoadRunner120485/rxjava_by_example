package de.sturmm.rxdemo.gateway.repository;

import de.sturmm.rxdemo.gateway.model.Car;
import de.sturmm.rxdemo.gateway.model.Customer;
import de.sturmm.rxdemo.gateway.model.Logbook;
import io.reactivex.Single;

/**
 * Created by sturmm on 06.06.17.
 */
public interface CustomerRepository {

    Single<Customer> findCustomer(String id);

    Single<Car> findCar(String id);

    Single<Logbook> findLogbook(String id);

}
