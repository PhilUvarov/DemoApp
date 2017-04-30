package com.philuvarov.demoapp;

import android.app.Application;
import android.support.annotation.NonNull;

import com.philuvarov.demoapp.di.component.AppComponent;
import com.philuvarov.demoapp.di.component.DaggerAppComponent;
import com.philuvarov.demoapp.di.module.AppModule;

public class DemoApp extends Application {

    private final AppComponent component = createComponent();

    @NonNull
    protected AppComponent createComponent() {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    @NonNull
    public AppComponent component() {
        return component;
    }

}
