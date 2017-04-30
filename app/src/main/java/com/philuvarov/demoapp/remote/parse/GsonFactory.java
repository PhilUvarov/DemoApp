package com.philuvarov.demoapp.remote.parse;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

    private GsonFactory(){}

    @NonNull
    public static Gson create() {
        return new GsonBuilder()
                .create();
    }

}
