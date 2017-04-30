package com.philuvarov.demoapp.country_details;

import android.support.annotation.NonNull;

import com.philuvarov.demoapp.remote.Api;
import com.philuvarov.demoapp.util.SchedulersFactory;

import io.reactivex.Single;

public class CountryDetailsInteractor {
    @NonNull
    private final Api api;
    @NonNull
    private final SchedulersFactory schedulers;

    public CountryDetailsInteractor(@NonNull Api api,
                                    @NonNull SchedulersFactory schedulers) {
        this.api = api;
        this.schedulers = schedulers;
    }

    public Single<CountryDetails> loadCountryDetails(@NonNull String name) {
        return api
                .getCountryDetails(name)
                .subscribeOn(schedulers.io());
    }
}
