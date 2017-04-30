package com.philuvarov.demoapp.di.component;

import com.philuvarov.demoapp.countries_list.CountriesListActivity;
import com.philuvarov.demoapp.di.PerActivity;
import com.philuvarov.demoapp.di.module.CountriesListModule;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = CountriesListModule.class)
public interface CountriesListComponent {

    void inject(CountriesListActivity activity);

}
