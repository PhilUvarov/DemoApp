package com.philuvarov.demoapp.countries_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.philuvarov.demoapp.R;
import com.philuvarov.demoapp.base.BaseActivity;
import com.philuvarov.demoapp.country_details.CountryDetailsActivity;
import com.philuvarov.demoapp.di.module.CountriesListModule;

import javax.inject.Inject;

public class CountriesListActivity extends BaseActivity implements CountriesListRouter {

    @Inject
    CountriesListPresenter presenter;

    @Nullable
    CountriesListView view;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_movies_list);
        getAppComponent()
                .plus(new CountriesListModule(
                                getParcelable(state, PRESENTER_STATE)
                        )
                )
                .inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (view == null) view = new CountriesListView(getContainerView(), presenter);
        presenter.attachView(view);
        presenter.attachRouter(this);
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        presenter.detachRouter();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(PRESENTER_STATE, presenter.saveState());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void openCountryDetails(@NonNull String code) {
        Intent intent = new Intent(this, CountryDetailsActivity.class);
        intent.putExtra(CountryDetailsActivity.EXTRA_COUNTRY_CODE, code);
        startActivity(intent);
    }

    private static final String PRESENTER_STATE = "presenter_state";

}
