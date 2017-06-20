
package de.sturmm.rxdemo.rest.repository;

import de.sturmm.rxdemo.rest.model.carpool.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by sturmm on 06.06.17.
 */
public interface CarRepository extends CrudRepository<Car, String> {

    List<Car> findByCustomerId(String logbookId);

}
