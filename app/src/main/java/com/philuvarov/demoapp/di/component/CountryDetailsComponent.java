package com.philuvarov.demoapp.di.component;

import com.philuvarov.demoapp.country_details.CountryDetailsActivity;
import com.philuvarov.demoapp.di.PerActivity;
import com.philuvarov.demoapp.di.module.CountryDetailsModule;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = CountryDetailsModule.class)
public interface CountryDetailsComponent {

    void inject(CountryDetailsActivity countryDetailsActivity);

}
