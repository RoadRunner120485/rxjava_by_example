package de.sturmm.rxdemo.gateway.rxutils;

import io.reactivex.Flowable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableFromFuture;
import io.reactivex.internal.operators.flowable.FlowableSingleSingle;
import io.reactivex.plugins.RxJavaPlugins;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Created by sturmm on 20.06.17.
 */
public class RxHelper {

    private RxHelper() {
        // prevent instantiation
    }

    public static <T> io.reactivex.Single<T> single(ListenableFuture<T> future) {
        ObjectHelper.requireNonNull(future, "future is null");

        final Flowable<T> source = RxJavaPlugins.onAssembly(new FlowableFromListenableFuture<>(future));
        return RxJavaPlugins.onAssembly(new FlowableSingleSingle<T>(source, null));
    }

    public static <T> io.reactivex.Flowable<T> flowable(ListenableFuture<T> future) {
        ObjectHelper.requireNonNull(future, "future is null");
        return RxJavaPlugins.onAssembly(new FlowableFromListenableFuture<>(future));
    }
}
