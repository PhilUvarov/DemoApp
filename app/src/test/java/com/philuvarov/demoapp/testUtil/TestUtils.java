package com.philuvarov.demoapp.testUtil;


import android.support.annotation.NonNull;

import com.philuvarov.demoapp.util.SchedulersFactory;

import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.when;

public final class TestUtils {

    private TestUtils() {
    }

    @NonNull
    public static SchedulersFactory mockSchedulers() {
        SchedulersFactory schedulers = Mockito.mock(SchedulersFactory.class);
        when(schedulers.mainThread()).thenReturn(Schedulers.trampoline());
        when(schedulers.computation()).thenReturn(Schedulers.trampoline());
        when(schedulers.io()).thenReturn(Schedulers.trampoline());
        return schedulers;
    }

    public static <T> void executeImmediately(Single<T> observable) {
        observable.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe();
    }

    public static <T> void executeImmediately(Observable<T> observable) {
        observable.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe();
    }

    public static <T> TestObserver<T> immediatelyOnTestObserver(Single<T> observable) {
        TestObserver<T> subscriber = new TestObserver<>();
        observable.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribeWith(subscriber);
        return subscriber;
    }

}
