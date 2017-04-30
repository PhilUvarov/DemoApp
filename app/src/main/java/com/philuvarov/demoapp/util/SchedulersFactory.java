package com.philuvarov.demoapp.util;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulersFactory {

    public Scheduler trampoline() {
        return Schedulers.trampoline();
    }

    public Scheduler newThread() {
        return Schedulers.newThread();
    }

    public Scheduler computation() {
        return Schedulers.computation();
    }

    public Scheduler io() {
        return Schedulers.io();
    }

    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

}
