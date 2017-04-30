package com.philuvarov.demoapp.di.component;

import com.philuvarov.demoapp.di.module.ApiModule;
import com.philuvarov.demoapp.di.module.AppModule;
import com.philuvarov.demoapp.di.module.CountriesListModule;
import com.philuvarov.demoapp.di.module.CountryDetailsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApiModule.class,
        AppModule.class,
})
public interface AppComponent {

    CountriesListComponent plus(CountriesListModule module);

    CountryDetailsComponent plus(CountryDetailsModule countryDetailsModule);

}
