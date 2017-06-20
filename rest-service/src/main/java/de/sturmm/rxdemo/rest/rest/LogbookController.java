package de.sturmm.rxdemo.rest.rest;

import de.sturmm.rxdemo.rest.model.carpool.LogRecord;
import de.sturmm.rxdemo.rest.model.carpool.Logbook;
import de.sturmm.rxdemo.rest.repository.LogRecordRepository;
import de.sturmm.rxdemo.rest.repository.LogbookRepository;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by sturmm on 06.06.17.
 */
@RestController
@RequestMapping("/logbooks")
@ExposesResourceFor(Logbook.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogbookController {

    private final LogbookRepository logbookRepository;
    private final LogRecordRepository logRecordRepository;

    @GetMapping(path = "/{lid}")
    public Single<Resource<Logbook>> findLogbookById(@PathVariable("lid") String logbookId) {
        return Single.fromCallable(() -> logbookRepository.findOne(logbookId))
                .map(LogbookController::toLogbookResource);
    }

    static Resource<Logbook> toLogbookResource(Logbook logbook) {
        final Resource<Logbook> result = new Resource<>(logbook);

        result.add(linkTo(methodOn(LogbookController.class).findLogbookById(logbook.getId())).withSelfRel());
        result.add(linkTo(methodOn(LogbookController.class).findLogRecords(logbook.getId())).withRel("logrecords"));
        return result;
    }

    @GetMapping(path = "/{lid}/records")
    public Single<Resources<Resource<LogRecord>>> findLogRecords(@PathVariable("lid") String logbookId) {
        return Single.fromCallable(() -> logRecordRepository.findByLogbookId(logbookId))
                .map(records -> {
                    final List<Resource<LogRecord>> recordResources = records.stream()
                            .map(LogRecordController::toLogRecordResource)
                            .collect(toList());

                    final Resources<Resource<LogRecord>> result = new Resources<>(recordResources);

                    result.add(linkTo(methodOn(LogbookController.class).findLogRecords(logbookId)).withSelfRel());

                    return result;
                });
    }

}
