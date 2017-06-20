package de.sturmm.rxdemo.gateway.repository;

import de.sturmm.rxdemo.gateway.model.Car;
import de.sturmm.rxdemo.gateway.model.Customer;
import de.sturmm.rxdemo.gateway.model.LogRecord;
import de.sturmm.rxdemo.gateway.model.Logbook;
import de.sturmm.rxdemo.gateway.model.PersonName;
import de.sturmm.rxdemo.gateway.repository.api.CarResource;
import de.sturmm.rxdemo.gateway.repository.api.CustomerResource;
import de.sturmm.rxdemo.gateway.repository.api.LogbookResource;
import de.sturmm.rxdemo.gateway.rxutils.RxHelper;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.HttpServerErrorException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by sturmm on 08.06.17.
 */
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final AsyncRestOperations client;

    @Autowired
    public CustomerRepositoryImpl(AsyncRestOperations client) {
        this.client = client;
    }

    @Override
    public Single<Customer> findCustomer(String id) {
        final ParameterizedTypeReference<Resource<CustomerResource>> type = new ParameterizedTypeReference<Resource<CustomerResource>>() {
        };
        // FUTURE ISN'T PULL BASED!
        return RxHelper.single(
                client.exchange("http://localhost:8080/customers/{id}", HttpMethod.GET, HttpEntity.EMPTY, type, id))
                .subscribeOn(Schedulers.io())
                .map(HttpEntity::getBody)
                .flatMap(this::createCustomer);
    }

    private Single<Customer> createCustomer(Resource<CustomerResource> customer) {
        return findCarsByUrl(customer.getLink("cars"))
                .map(cars -> {
                    final CustomerResource source = customer.getContent();
                    return Customer.builder()
                            .id(source.getId())
                            .name(new PersonName(source.getName().getFirstName(), source.getName().getLastName()))
                            .cars(cars)
                            .build();
                });
    }

    @Override
    public Single<Car> findCar(String id) {
        return null;
    }

    private Single<List<Car>> findCarsByUrl(Link url) {
        final ParameterizedTypeReference<Resources<Resource<CarResource>>> type = new ParameterizedTypeReference<Resources<Resource<CarResource>>>() {
        };
        return RxHelper.flowable(client.exchange(url.getHref(), HttpMethod.GET, HttpEntity.EMPTY, type))
                .subscribeOn(Schedulers.io())
                .map(HttpEntity::getBody)
                .flatMapIterable(Resources::getContent)
                .flatMapSingle(this::createCar)
                .collect(LinkedList::new, List::add);
    }

    private Single<Car> createCar(Resource<CarResource> carResource) {
        return Single.just(carResource)
                .subscribeOn(Schedulers.io())
                .flatMap(res -> findLogbookByUrl(res.getLink("logbook"))
                        .map(logbook -> Car.builder()
                                .id(res.getContent().getId())
                                .brand(res.getContent().getBrand())
                                .model(res.getContent().getModel())
                                .logbook(logbook)
                                .build())
                );
    }

    private Single<Logbook> findLogbookByUrl(Link link) {
        final ParameterizedTypeReference<Resource<LogbookResource>> type = new ParameterizedTypeReference<Resource<LogbookResource>>() {};
        return Single.just(link.getHref())
                // we could not use RxHelper.fromFuture directly because retry will not work then
                .flatMap(url ->  RxHelper.single(client.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, type)))
                .retry(e -> e instanceof HttpServerErrorException)
                .subscribeOn(Schedulers.io())
                .map(HttpEntity::getBody)
                .flatMap(res -> findLogRecords(res.getLink("logrecords"))
                        .map(records -> {
                            final LogbookResource lb = res.getContent();
                            return Logbook.builder().id(lb.getId()).name(lb.getName()).records(records).build();
                        }));
    }

    private Single<List<LogRecord>> findLogRecords(Link url) {
        final ParameterizedTypeReference<Resources<Resource<LogRecord>>> type = new ParameterizedTypeReference<Resources<Resource<LogRecord>>>() {
        };
        return RxHelper.flowable(client.exchange(url.getHref(), HttpMethod.GET, HttpEntity.EMPTY, type))
                .map(HttpEntity::getBody)
                .flatMapIterable(Resources::getContent)
                .map(Resource::getContent)
                .map(log ->
                              LogRecord.builder()
                                      .id(log.getId())
                                      .startMileage(log.getStartMileage())
                                      .endMileage(log.getEndMileage())
                                      .startTimestamp(log.getStartTimestamp())
                                      .endTimestamp(log.getEndTimestamp())
                                      .build()
                 )
                 .collect(LinkedList::new, List::add);
    }


    @Override
    public Single<Logbook> findLogbook(String id) {
        return null;
    }

}
