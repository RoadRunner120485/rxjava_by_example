package de.sturmm.rxdemo.rest.repository;

import de.sturmm.rxdemo.rest.model.customer.Customer;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by sturmm on 06.06.17.
 */
public interface CustomerRepository extends CrudRepository<Customer, String> {

    @Transactional(Transactional.TxType.MANDATORY)
    default Customer updateOnTx(Customer customer) {
        return save(customer);
    }

}
