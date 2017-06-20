package de.sturmm.rxdemo.rest.rest;

import de.sturmm.rxdemo.rest.model.carpool.LogRecord;
import de.sturmm.rxdemo.rest.repository.LogRecordRepository;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by sturmm on 06.06.17.
 */
@RestController
@RequestMapping("/logrecords")
@ExposesResourceFor(LogRecord.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogRecordController {

    private final LogRecordRepository logRecordRepository;

    @GetMapping(path = "/{rid}")
    public Single<Resource<LogRecord>> loadLogbookRecordsById(@PathVariable("rid") String recordId) {
        return Single.fromCallable(() -> logRecordRepository.findOne(recordId))
                .map(LogRecordController::toLogRecordResource);
    }

    static Resource<LogRecord> toLogRecordResource(LogRecord record) {
        final Resource<LogRecord> result = new Resource<>(record);

        result.add(linkTo(methodOn(LogRecordController.class).loadLogbookRecordsById(record.getId())).withSelfRel());
        result.add(linkTo(methodOn(CustomerController.class).loadCustomerById(record.getCustomerId())).withRel("customer"));
        result.add(linkTo(methodOn(CarController.class).loadCarById(record.getCustomerId())).withRel("car"));
        result.add(linkTo(methodOn(LogbookController.class).findLogbookById(record.getLogbookId())).withRel("logbook"));

        return result;
    }


}
