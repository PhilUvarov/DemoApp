package com.philuvarov.demoapp.countries_list;

import android.support.annotation.Nullable;

import com.philuvarov.demoapp.countries_list.adapter.CountryItem;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

@Parcel
public class CountriesListPresenterState {

    @Nullable
    public final List<CountryItem> countries;

    @ParcelConstructor
    public CountriesListPresenterState(@Nullable List<CountryItem> countries) {
        this.countries = countries;
    }

}
