package com.philuvarov.demoapp.countries_list;


import android.support.annotation.NonNull;

import com.philuvarov.demoapp.remote.Api;
import com.philuvarov.demoapp.remote.model.CountryInfo;
import com.philuvarov.demoapp.util.SchedulersFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static com.philuvarov.demoapp.testUtil.TestUtils.executeImmediately;
import static com.philuvarov.demoapp.testUtil.TestUtils.immediatelyOnTestObserver;
import static com.philuvarov.demoapp.testUtil.TestUtils.mockSchedulers;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CountriesListInteractorTest {

    @Mock private Api api;

    private SchedulersFactory schedulers = mockSchedulers();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        when(api.getCountryList()).thenReturn(Single.never());
    }

    @Test
    public void getCountries_forwardsCallToApi_always() {
        CountriesListInteractor interactor = createInteractor();

        executeImmediately(interactor.getCountries());

        verify(api).getCountryList();
    }

    @Test
    public void getCountries_returnsCountriesFromResponse_always() {
        CountryInfo countryInfo = randomCountryInfo();
        CountriesListInteractor interactor = createInteractor();
        givenApiResponse(Single.just(singletonList(countryInfo)));

        TestObserver<List<CountryInfo>> observer = immediatelyOnTestObserver(interactor.getCountries());

        observer.assertValueCount(1);
        observer.assertValueAt(0, countries -> countries.contains(countryInfo));
    }

    @NonNull
    private CountryInfo randomCountryInfo() {
        return new CountryInfo("foo", "");
    }

    private void givenApiResponse(Single<List<CountryInfo>> response) {
        when(api.getCountryList()).thenReturn(response);
    }

    private CountriesListInteractor createInteractor() {
        return new CountriesListInteractor(api, schedulers);
    }

}
