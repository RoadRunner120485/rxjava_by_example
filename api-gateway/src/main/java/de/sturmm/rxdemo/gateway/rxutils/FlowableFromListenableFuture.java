/**
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package de.sturmm.rxdemo.gateway.rxutils;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import org.reactivestreams.Subscriber;
import org.springframework.util.concurrent.ListenableFuture;

public final class FlowableFromListenableFuture<T> extends Flowable<T> {
    final ListenableFuture<? extends T> future;

    public FlowableFromListenableFuture(ListenableFuture<? extends T> future) {
        this.future = future;
    }

    @Override
    public void subscribeActual(Subscriber<? super T> s) {
        DeferredScalarSubscription<T> deferred = new DeferredScalarSubscription<T>(s) {
            @Override
            public void cancel() {
                future.cancel(true);
                super.cancel();
            }
        };
        s.onSubscribe(deferred);
        future.addCallback(
                success -> {
                    if (success == null) {
                        s.onError(new NullPointerException("The future returned null"));
                    } else {
                        deferred.complete(success);
                    }
                },
                error -> {
                    Exceptions.throwIfFatal(error);
                    if (!deferred.isCancelled()) {
                        s.onError(error);
                    }
                }
        );
    }
}
