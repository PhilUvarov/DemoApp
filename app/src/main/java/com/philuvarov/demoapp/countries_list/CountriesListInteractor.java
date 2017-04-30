package com.philuvarov.demoapp.countries_list;


import android.support.annotation.NonNull;

import com.philuvarov.demoapp.remote.Api;
import com.philuvarov.demoapp.remote.model.CountryInfo;
import com.philuvarov.demoapp.util.SchedulersFactory;

import java.util.List;

import io.reactivex.Single;

public class CountriesListInteractor {

    @NonNull
    private final Api api;
    @NonNull
    private final SchedulersFactory schedulers;

    public CountriesListInteractor(@NonNull Api api,
                                   @NonNull SchedulersFactory schedulers) {
        this.api = api;
        this.schedulers = schedulers;
    }

    @NonNull
    public Single<List<CountryInfo>> getCountries() {
        return api
                .getCountryList()
                .subscribeOn(schedulers.io());
    }

}
