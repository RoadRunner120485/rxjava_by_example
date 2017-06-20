package de.sturmm.rxdemo.rest.rest;

import de.sturmm.rxdemo.rest.model.carpool.Car;
import de.sturmm.rxdemo.rest.model.carpool.Logbook;
import de.sturmm.rxdemo.rest.repository.CarRepository;
import de.sturmm.rxdemo.rest.repository.LogbookRepository;
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
@RequestMapping("/cars")
@ExposesResourceFor(Car.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CarController {

    private final CarRepository carRepository;
    private final LogbookRepository logbookRepository;

    private AtomicBoolean error = new AtomicBoolean(false);

    @GetMapping(path = "/{carId}")
    public Single<Resource<Car>> loadCarById(@PathVariable("carId") String carId) {
        return Single.fromCallable(() -> carRepository.findOne(carId))
                .map(CarController::toCarResource);
    }

    @GetMapping(path = "/{carId}/logbook")
    public Single<Resource<Logbook>> loadLogbookByCarId(@PathVariable("carId") String carId) {
        return Single.fromCallable(() -> logbookRepository.findByCarId(carId))
                .flatMap(lb -> {
                    if (error.getAndSet(!error.get())) {
                        return Single.error(new RuntimeException());
                    } else {
                        return Single.just(lb);
                    }
                })
                .map(LogbookController::toLogbookResource);
    }


    static Resource<Car> toCarResource(Car source) {
        final Resource<Car> result = new Resource<>(source);

        result.add(linkTo(methodOn(CarController.class).loadCarById(source.getId())).withSelfRel());
        result.add(linkTo(methodOn(CustomerController.class).loadCustomerById(source.getCustomerId())).withRel("customer"));
        result.add(linkTo(methodOn(CarController.class).loadLogbookByCarId(source.getId())).withRel("logbook"));

        return result;
    }


}
