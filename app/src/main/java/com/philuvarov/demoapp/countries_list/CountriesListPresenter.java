package com.philuvarov.demoapp.countries_list;


import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.philuvarov.demoapp.countries_list.adapter.CountriesAdapter;
import com.philuvarov.demoapp.countries_list.adapter.CountryItem;
import com.philuvarov.demoapp.remote.model.CountryInfo;
import com.philuvarov.demoapp.util.Converter;
import com.philuvarov.demoapp.util.SchedulersFactory;

import org.parceler.Parcels;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class CountriesListPresenter implements CountriesAdapter.CountryClickListener {

    @NonNull
    private final CountriesListInteractor interactor;
    @NonNull
    private final SchedulersFactory schedulers;
    @NonNull
    private final Converter<CountryInfo, CountryItem> itemConverter;
    @NonNull
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Nullable
    private CountriesListView view;
    @Nullable
    private List<CountryItem> countries;
    @Nullable
    private CountriesListRouter router;

    public CountriesListPresenter(@NonNull CountriesListInteractor interactor,
                                  @NonNull SchedulersFactory schedulers,
                                  @NonNull Converter<CountryInfo, CountryItem> itemConverter,
                                  @Nullable CountriesListPresenterState state) {

        this.interactor = interactor;
        this.schedulers = schedulers;
        this.itemConverter = itemConverter;
        if (state != null) {
            initializeState(state);
        }

    }

    private void initializeState(@NonNull CountriesListPresenterState state) {
        this.countries = state.countries;
    }

    public void detachView() {
        disposables.clear();
        view = null;
    }

    public void attachView(@NonNull CountriesListView view) {
        this.view = view;
        postInitData(view);
    }

    private void postInitData(@NonNull CountriesListView view) {
        if (countries != null && !countries.isEmpty()) {
            view.setData(countries);
        } else {
            loadInitialData(view);
        }
    }

    private void loadInitialData(@NonNull CountriesListView view) {
        view.showLoading();
        disposables.add(
                interactor
                        .getCountries()
                        .observeOn(schedulers.computation())
                        .flattenAsObservable(countryInfos -> countryInfos)
                        .map(itemConverter::convert)
                        .toList()
                        .observeOn(schedulers.mainThread())
                        .subscribe(this::onCountriesLoaded, this::onError));
    }

    private void onError(@NonNull Throwable error) {
        error.printStackTrace();
        //noinspection ConstantConditions
        view.hideLoading();
        view.showError();
    }

    private void onCountriesLoaded(@NonNull List<CountryItem> countries) {
        //noinspection ConstantConditions
        view.hideLoading();
        this.countries = countries;
        view.setData(countries);
    }

    @NonNull
    public Parcelable saveState() {
        return Parcels.wrap(new CountriesListPresenterState(countries));
    }

    @Override
    public void onCountryClicked(@NonNull String code) {
        if (router == null) return;
        router.openCountryDetails(code);
    }

    public void attachRouter(@NonNull CountriesListRouter router) {
        this.router = router;
    }

    public void detachRouter() {
        this.router = null;
    }

}
