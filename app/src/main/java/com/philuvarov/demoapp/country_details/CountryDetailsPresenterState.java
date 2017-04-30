package com.philuvarov.demoapp.country_details;

import android.support.annotation.Nullable;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class CountryDetailsPresenterState {

    @Nullable
    public final CountryDetails countryDetails;

    @ParcelConstructor
    public CountryDetailsPresenterState(@Nullable CountryDetails countryDetails) {
        this.countryDetails = countryDetails;
    }
}
