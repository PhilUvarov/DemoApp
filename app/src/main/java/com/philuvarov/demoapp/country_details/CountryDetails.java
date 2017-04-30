package com.philuvarov.demoapp.country_details;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class CountryDetails {
    @NonNull
    @SerializedName("flag")
    public final String flagUrl;
    @NonNull
    @SerializedName("capital")
    public final String capital;
    @NonNull
    @SerializedName("population")
    public final String population;
    @NonNull
    @SerializedName("name")
    public final String countryName;

    @ParcelConstructor
    public CountryDetails(@NonNull String flagUrl,
                          @NonNull String capital,
                          @NonNull String population,
                          @NonNull String countryName) {
        this.flagUrl = flagUrl;
        this.capital = capital;
        this.population = population;
        this.countryName = countryName;
    }
}
