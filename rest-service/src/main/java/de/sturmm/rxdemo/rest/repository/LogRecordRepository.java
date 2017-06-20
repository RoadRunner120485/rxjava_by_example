package de.sturmm.rxdemo.rest.repository;

import de.sturmm.rxdemo.rest.model.carpool.LogRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by sturmm on 06.06.17.
 */
public interface LogRecordRepository extends CrudRepository<LogRecord, String> {

    List<LogRecord> findByLogbookId(String logbookId);

}
