package com.philuvarov.demoapp.country_details;

import android.support.annotation.NonNull;

import com.philuvarov.demoapp.util.SchedulersFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;

import static com.philuvarov.demoapp.testUtil.TestUtils.mockSchedulers;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.parceler.Parcels.unwrap;

public class CountryDetailsPresenterTest {
    @Mock
    private CountryDetailsView view;
    @Mock
    private CountryDetailsInteractor interactor;

    private SchedulersFactory schedulers = mockSchedulers();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private CountryDetailsPresenter presenter;

    @Before
    public void setUp() {
        presenter = createPresenter("");
        when(interactor.loadCountryDetails(any())).thenReturn(Single.never());
    }

    @Test
    public void attachView_tellsInteractorToLoadCountryDetails_noSavedState() {
        CountryDetailsPresenter presenter = createPresenter("code");

        presenter.attachView(view);

        verify(interactor).loadCountryDetails("code");
    }

    @Test
    public void attachView_setsCountryDetailsToView_withSavedState() {
        CountryDetails initialItem = countryItem();
        CountryDetailsPresenterState state = new CountryDetailsPresenterState(initialItem);
        CountryDetailsPresenter presenter = createPresenterWithState(state);

        presenter.attachView(view);

        assertItemPassedToView(initialItem);
    }

    @Test
    public void attachView_doesntTriggerLoad_withSavedState() {
        CountryDetailsPresenterState state = new CountryDetailsPresenterState(countryItem());
        CountryDetailsPresenter presenter = createPresenterWithState(state);

        presenter.attachView(view);

        verify(interactor, never()).loadCountryDetails(any());
    }

    @Test
    public void loadInitData_passesDataToView_dataLoadedSuccessfully() {
        CountryDetails initialItem = countryItem();
        givenCountryDetailsResponse(Single.just(initialItem));

        presenter.attachView(view);

        assertItemPassedToView(initialItem);
    }

    @Test
    public void loadInitData_tellsViewToShowError_onError() {
        givenCountryDetailsResponse(Single.error(new Throwable()));

        presenter.attachView(view);

        verify(view).showError();
    }

    @Test
    public void saveState_returnsStateWithLoadedCountryDetails_always() {
        CountryDetails initialItem = countryItem();
        givenCountryDetailsResponse(Single.just(initialItem));
        presenter.attachView(view);

        CountryDetailsPresenterState state = unwrap(presenter.saveState());

        assertThat(state.countryDetails, is(notNullValue()));
        assertThat(state.countryDetails, is(initialItem));
    }

    private void assertItemPassedToView(CountryDetails initialItem) {
        ArgumentCaptor<CountryDetails> captor = forClass(CountryDetails.class);
        verify(view).bindData(captor.capture());
        CountryDetails passedItem = captor.getValue();
        assertThat(passedItem, is(initialItem));
    }

    private CountryDetails countryItem() {
        return new CountryDetails("", "", "", "");
    }

    private void givenCountryDetailsResponse(Single<CountryDetails> response) {
        when(interactor.loadCountryDetails(any())).thenReturn(response);
    }

    private CountryDetailsPresenter createPresenter(@NonNull String countryCode) {
        return new CountryDetailsPresenter(
                countryCode,
                interactor,
                schedulers,
                null
        );
    }

    private CountryDetailsPresenter createPresenterWithState(CountryDetailsPresenterState state) {
        return new CountryDetailsPresenter(
                "code",
                interactor,
                schedulers,
                state
        );
    }
}