package org.mockito.configuration;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.FutureAdapter;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by sturmm on 02/05/2017.
 */
public class MockitoConfiguration extends DefaultMockitoConfiguration {

    public Answer<Object> getDefaultAnswer() {
        return new ReturnsEmptyValues() {
            @Override
            public Object answer(InvocationOnMock inv) {
                Class<?> type = inv.getMethod().getReturnType();
                if (type.isAssignableFrom(Observable.class)) {
                    return Observable.error(createException(inv));
                } else if (type.isAssignableFrom(Single.class)) {
                    return Single.error(createException(inv));
                } else if(type.isAssignableFrom(ListenableFuture.class)) {
                    return AsyncResult.forExecutionException(createException(inv));
                } {
                    return super.answer(inv);
                }
            }
        };
    }

    private
    RuntimeException createException(InvocationOnMock invocation) {
        return new RuntimeException("No mock defined for invocation " + invocation.toString());
    }

}
