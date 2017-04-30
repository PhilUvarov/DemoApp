package com.philuvarov.demoapp.di.module;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.philuvarov.demoapp.countries_list.CountryInfoItemConverter;
import com.philuvarov.demoapp.remote.Api;
import com.philuvarov.demoapp.countries_list.CountriesListInteractor;
import com.philuvarov.demoapp.countries_list.CountriesListPresenter;
import com.philuvarov.demoapp.countries_list.adapter.CountryItem;
import com.philuvarov.demoapp.di.PerActivity;
import com.philuvarov.demoapp.remote.model.CountryInfo;
import com.philuvarov.demoapp.util.Converter;
import com.philuvarov.demoapp.util.SchedulersFactory;

import org.parceler.Parcels;

import dagger.Module;
import dagger.Provides;

@Module
public class CountriesListModule {

    @Nullable
    private final Parcelable presenterState;

    public CountriesListModule(@Nullable Parcelable presenterState) {
        this.presenterState = presenterState;
    }

    @Provides
    @PerActivity
    CountriesListPresenter countriesListPresenter(CountriesListInteractor interactor,
                                                  SchedulersFactory schedulersFactory,
                                                  Converter<CountryInfo, CountryItem> converter) {

        return new CountriesListPresenter(
                interactor,
                schedulersFactory,
                converter,
                Parcels.unwrap(presenterState)
        );
    }

    @Provides
    @PerActivity
    CountriesListInteractor countriesListInteractor(Api api,
                                                    SchedulersFactory schedulersFactory) {
        return new CountriesListInteractor(
                api,
                schedulersFactory
        );
    }

    @Provides
    @PerActivity
    Converter<CountryInfo, CountryItem> itemConverter() {
        return new CountryInfoItemConverter();
    }

}
