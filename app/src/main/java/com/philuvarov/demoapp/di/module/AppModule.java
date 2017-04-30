package com.philuvarov.demoapp.di.module;


import android.app.Application;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.philuvarov.demoapp.DemoApp;
import com.philuvarov.demoapp.util.SchedulersFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @NonNull
    private final DemoApp app;

    public AppModule(@NonNull DemoApp application) {
        app = application;
    }

    @Provides
    @Singleton
    public Resources provideResources() {
        return app.getResources();
    }

    @Provides
    @Singleton
    public SchedulersFactory schedulersFactory() {
        return new SchedulersFactory();
    }

    @Provides
    @Singleton
    public Application application() {
        return app;
    }
}
