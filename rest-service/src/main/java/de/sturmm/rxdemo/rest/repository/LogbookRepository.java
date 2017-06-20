package de.sturmm.rxdemo.rest.repository;

import de.sturmm.rxdemo.rest.model.carpool.Logbook;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sturmm on 06.06.17.
 */
public interface LogbookRepository extends CrudRepository<Logbook, String> {

    Logbook findByCarId(String carId);

}
