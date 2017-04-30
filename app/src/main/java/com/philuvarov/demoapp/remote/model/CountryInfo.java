package com.philuvarov.demoapp.remote.model;


import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CountryInfo {

    @NonNull
    @SerializedName("name")
    public final String name;

    @NonNull
    @SerializedName("alpha2Code")
    public final String code;


    public CountryInfo(@NonNull String name, @NonNull String code) {
        this.name = name;
        this.code = code;
    }

}
