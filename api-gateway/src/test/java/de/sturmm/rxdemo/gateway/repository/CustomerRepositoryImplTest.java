package de.sturmm.rxdemo.gateway.repository;

import de.sturmm.rxdemo.gateway.model.Customer;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.AsyncRestOperations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by sturmm on 19.06.17.
 */
public class CustomerRepositoryImplTest {

    private CustomerRepositoryImpl underTest;
    private AsyncRestOperations restClient;

    @Before
    public void setUp() throws Exception {
        restClient = mock(AsyncRestOperations.class);
        underTest = new CustomerRepositoryImpl(restClient);
    }

    @Test
    public void findCustomerRestCallNotExecuted() throws Exception {
        // --- given: nothing

        // --- when: there is no susbscriber
        final Single<Customer> result = underTest.findCustomer("abcdef");

        //--- then: no http call must be executed
        verifyNoMoreInteractions(restClient);
    }

    @Test
    public void findCustomerAllFine() throws Exception {
        // --- given: ...

        // --- when: there is a subscriber
        final TestObserver<Customer> testObserver = underTest.findCustomer("abcdef")
                .test();

        //--- then: ...
        testObserver.awaitTerminalEvent();
        testObserver.assertValue(customer -> {
            // ... assert
            return customer != null;
        });
        verifyNoMoreInteractions(restClient);
    }
}