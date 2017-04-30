package com.philuvarov.demoapp.di.module;


import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.philuvarov.demoapp.BuildConfig;
import com.philuvarov.demoapp.remote.Api;
import com.philuvarov.demoapp.remote.parse.GsonFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public Gson gson() {
        return GsonFactory.create();
    }

    @Provides
    @Singleton
    public Api api(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    @Provides
    @Singleton
    public Retrofit retrofit(OkHttpClient httpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    @Provides
    @Singleton
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG)
        clientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(BASIC));

        return clientBuilder.build();
    }


}
