package com.philuvarov.demoapp.di.module;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.philuvarov.demoapp.country_details.CountryDetailsInteractor;
import com.philuvarov.demoapp.country_details.CountryDetailsPresenter;
import com.philuvarov.demoapp.di.PerActivity;
import com.philuvarov.demoapp.remote.Api;
import com.philuvarov.demoapp.util.SchedulersFactory;

import org.parceler.Parcels;

import dagger.Module;
import dagger.Provides;

@Module
public class CountryDetailsModule {

    @NonNull
    private final String countryCode;
    @Nullable
    private final Parcelable presenterState;

    public CountryDetailsModule(@NonNull String countryCode,
                                @Nullable Parcelable presenterState) {
        this.countryCode = countryCode;
        this.presenterState = presenterState;
    }

    @Provides
    @PerActivity
    CountryDetailsPresenter countryDetailsPresenter(CountryDetailsInteractor interactor,
                                                    SchedulersFactory schedulersFactory) {

        return new CountryDetailsPresenter(
                countryCode,
                interactor,
                schedulersFactory,
                Parcels.unwrap(presenterState)
        );
    }

    @Provides
    @PerActivity
    CountryDetailsInteractor countryDetailsInteractor(Api api,
                                                      SchedulersFactory schedulersFactory) {
        return new CountryDetailsInteractor(
                api,
                schedulersFactory
        );
    }

}
