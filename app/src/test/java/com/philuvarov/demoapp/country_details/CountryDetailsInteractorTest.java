package com.philuvarov.demoapp.country_details;

import android.support.annotation.NonNull;

import com.philuvarov.demoapp.remote.Api;
import com.philuvarov.demoapp.testUtil.TestUtils;
import com.philuvarov.demoapp.util.SchedulersFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static com.philuvarov.demoapp.testUtil.TestUtils.immediatelyOnTestObserver;
import static com.philuvarov.demoapp.testUtil.TestUtils.mockSchedulers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CountryDetailsInteractorTest {

    @Mock
    private Api api;

    private SchedulersFactory schedulers = mockSchedulers();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    CountryDetailsInteractor interactor;

    @Before
    public void setUp() {
        interactor = createInteractor();
        when(api.getCountryDetails(any())).thenReturn(Single.never());
    }

    @Test
    public void getCountryDetails_forwardsCallToApi_always() {
        TestUtils.executeImmediately(interactor.loadCountryDetails("code"));

        verify(api).getCountryDetails("code");
    }

    @Test
    public void getCountryDetails_returnsCountryDetailsFromResponse_always() {
        CountryDetails country = randomCountry();
        givenApiResponse(Single.just(country));

        TestObserver<CountryDetails> observer = immediatelyOnTestObserver(interactor.loadCountryDetails(""));

        observer.assertValueCount(1);
        observer.assertValueAt(0, countryDetails -> countryDetails == country);
    }

    @NonNull
    private CountryDetails randomCountry() {
        return new CountryDetails("", "", "", "");
    }

    private void givenApiResponse(Single<CountryDetails> response) {
        when(api.getCountryDetails(any())).thenReturn(response);
    }

    private CountryDetailsInteractor createInteractor() {
        return new CountryDetailsInteractor(api, schedulers);
    }

}