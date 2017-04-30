package com.philuvarov.demoapp.remote;


import android.support.annotation.NonNull;

import com.philuvarov.demoapp.country_details.CountryDetails;
import com.philuvarov.demoapp.remote.model.CountryInfo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("all")
    Single<List<CountryInfo>> getCountryList();

    @GET("alpha/{code}")
    Single<CountryDetails> getCountryDetails(@NonNull @Path("code") String code);
}
