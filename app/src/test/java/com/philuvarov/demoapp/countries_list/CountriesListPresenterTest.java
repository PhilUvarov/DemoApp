package com.philuvarov.demoapp.countries_list;

import com.philuvarov.demoapp.countries_list.adapter.CountryItem;
import com.philuvarov.demoapp.remote.model.CountryInfo;
import com.philuvarov.demoapp.util.Converter;
import com.philuvarov.demoapp.util.SchedulersFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.philuvarov.demoapp.testUtil.TestUtils.mockSchedulers;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_SMART_NULLS;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.parceler.Parcels.unwrap;

public class CountriesListPresenterTest {
    @Mock
    private CountriesListView view;
    @SuppressWarnings("unchecked")
    @Mock
    private Converter<CountryInfo, CountryItem> itemConverter = mock(Converter.class, RETURNS_SMART_NULLS);
    @Mock
    private CountriesListInteractor interactor;

    private SchedulersFactory schedulers = mockSchedulers();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        when(interactor.getCountries()).thenReturn(Single.never());
    }

    @Test
    public void attachView_tellsViewToShowLoading_noSavedState() {
        CountriesListPresenter presenter = createPresenter();

        presenter.attachView(view);

        verify(view).showLoading();
    }

    @Test
    public void attachView_tellsInteractorToLoadCountries_noSavedState() {
        CountriesListPresenter presenter = createPresenter();

        presenter.attachView(view);

        verify(interactor).getCountries();
    }

    @Test
    public void loadInitData_tellsViewToHideProgress_dataLoadedSuccessfully() {
        givenInteractorCountriesResponse(Single.just(emptyList()));
        CountriesListPresenter presenter = createPresenter();

        presenter.attachView(view);

        verify(view).hideLoading();
    }

    @Test
    public void attachView_setsCountriesToView_withSavedState() {
        CountryItem initialItem = createItem("foo");
        CountriesListPresenterState state = new CountriesListPresenterState(
                singletonList(initialItem)
        );
        CountriesListPresenter presenter = createPresenterWithState(state);

        presenter.attachView(view);

        assertItemPassedToView(initialItem);
    }


    @Test
    public void attachView_doesntTriggerLoad_withSavedState() {
        CountriesListPresenterState state = new CountriesListPresenterState(
                singletonList(randomItem())
        );
        CountriesListPresenter presenter = createPresenterWithState(state);

        presenter.attachView(view);

        verify(view, never()).showLoading();
        verify(interactor, never()).getCountries();
    }

    @Test
    public void loadInitData_passesDataToView_dataLoadedSuccessfully() {
        CountryItem initialItem = createItem("foo");
        givenInteractorCountriesResponse(Single.just(singletonList(randomCountry())));
        givenConverterResponse(initialItem);
        CountriesListPresenter presenter = createPresenter();

        presenter.attachView(view);

        assertItemPassedToView(initialItem);
    }

    @Test
    public void loadInitData_tellsViewToHideProgress_onError() {
        givenInteractorCountriesResponse(Single.error(new Throwable()));
        CountriesListPresenter presenter = createPresenter();

        presenter.attachView(view);

        verify(view).hideLoading();
        verify(view).showError();
    }

    @Test
    public void saveState_returnsStateWithConvertedItems_always() {
        CountryItem initialItem = createItem("foo");
        givenInteractorCountriesResponse(Single.just(singletonList(randomCountry())));
        givenConverterResponse(initialItem);
        CountriesListPresenter presenter = createPresenter();
        presenter.attachView(view);

        CountriesListPresenterState state = unwrap(presenter.saveState());

        assertThat(state.countries, is(notNullValue()));
        assertThat(state.countries, is(hasSize(1)));
        assertThat(state.countries.contains(initialItem), is(true));
    }

    private void assertItemPassedToView(CountryItem initialItem) {
        ArgumentCaptor<List<CountryItem>> captor = forClass(ArrayList.class);
        verify(view).setData(captor.capture());
        assertThat(captor.getValue().size(), is(1));
        CountryItem passedItem = captor.getValue().get(0);
        assertThat(passedItem, is(initialItem));
    }

    private CountriesListPresenter givenPresenterWithAttachedView() {
        CountriesListPresenter presenter = createPresenter();
        presenter.attachView(view);
        return presenter;
    }

    private void givenConverterResponse(CountryItem item) {
        when(itemConverter.convert(any())).thenReturn(item);
    }

    private CountryItem createItem(String name) {
        return new CountryItem(name, "");
    }

    private CountryItem randomItem() {
        return new CountryItem("", "");
    }

    private CountryInfo randomCountry() {
        return new CountryInfo("", "");
    }

    private void givenInteractorCountriesResponse(Single<List<CountryInfo>> response) {
        when(interactor.getCountries()).thenReturn(response);
    }

    private CountriesListPresenter createPresenter() {
        return new CountriesListPresenter(
                interactor,
                schedulers,
                itemConverter,
                null
        );
    }

    private CountriesListPresenter createPresenterWithState(CountriesListPresenterState state) {
        return new CountriesListPresenter(
                interactor,
                schedulers,
                itemConverter,
                state
        );
    }
}