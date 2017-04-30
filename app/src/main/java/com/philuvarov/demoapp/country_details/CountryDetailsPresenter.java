package com.philuvarov.demoapp.country_details;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.philuvarov.demoapp.util.SchedulersFactory;

import org.parceler.Parcels;

import io.reactivex.disposables.CompositeDisposable;

public class CountryDetailsPresenter {

    @Nullable
    private CountryDetails countryDetails;

    @NonNull
    private CompositeDisposable disposables = new CompositeDisposable();

    @NonNull
    private final String countryName;
    @NonNull
    private final SchedulersFactory schedulers;

    @NonNull
    private final CountryDetailsInteractor interactor;

    public CountryDetailsPresenter(@NonNull String countryName,
                                   @NonNull CountryDetailsInteractor interactor,
                                   @NonNull SchedulersFactory schedulers,
                                   @Nullable CountryDetailsPresenterState state) {
        this.countryName = countryName;
        this.interactor = interactor;
        this.schedulers = schedulers;
        if (state != null) {
            countryDetails = state.countryDetails;
        }
    }

    public void attachView(@NonNull CountryDetailsView view) {
        postInitData(view);
    }

    private void postInitData(@NonNull CountryDetailsView view) {
        if (countryDetails == null) {
            loadMovieDetails(view);
        } else {
            view.bindData(countryDetails);
        }
    }

    private void loadMovieDetails(@NonNull CountryDetailsView view) {
        disposables.add(
                interactor
                        .loadCountryDetails(countryName)
                        .observeOn(schedulers.mainThread())
                        .subscribe(details -> {
                            countryDetails = details;
                            view.bindData(details);
                        }, throwable -> view.showError())
        );
    }

    public void detachView() {
        disposables.clear();
    }

    public Parcelable saveState() {
        return Parcels.wrap(
                new CountryDetailsPresenterState(
                        countryDetails
                )
        );
    }

}
