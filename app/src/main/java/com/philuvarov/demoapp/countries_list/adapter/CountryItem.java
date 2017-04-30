package com.philuvarov.demoapp.countries_list.adapter;


import android.support.annotation.NonNull;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class CountryItem {

    @NonNull
    public final String name;

    @NonNull
    public final String code;

    @ParcelConstructor
    public CountryItem(@NonNull String name, @NonNull String code) {
        this.name = name;
        this.code = code;
    }

}
