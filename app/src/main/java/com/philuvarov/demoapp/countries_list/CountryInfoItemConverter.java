package com.philuvarov.demoapp.countries_list;

import android.support.annotation.NonNull;

import com.philuvarov.demoapp.countries_list.adapter.CountryItem;
import com.philuvarov.demoapp.remote.model.CountryInfo;
import com.philuvarov.demoapp.util.Converter;

public class CountryInfoItemConverter implements Converter<CountryInfo, CountryItem> {

    @NonNull
    @Override
    public CountryItem convert(@NonNull CountryInfo value) {
        return new CountryItem(value.name, value.code);
    }

}
