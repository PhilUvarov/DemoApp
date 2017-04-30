package com.philuvarov.demoapp.country_details;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.philuvarov.demoapp.R;
import com.philuvarov.demoapp.base.BaseActivity;
import com.philuvarov.demoapp.di.module.CountryDetailsModule;

import javax.inject.Inject;

public class CountryDetailsActivity extends BaseActivity {

    public static final String EXTRA_COUNTRY_CODE = "country_code";

    @Nullable
    private CountryDetailsView view;
    @Inject
    CountryDetailsPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_details);
        getAppComponent()
                .plus(new CountryDetailsModule(
                                getIntent().getStringExtra(EXTRA_COUNTRY_CODE),
                                getParcelable(savedInstanceState, PRESENTER_STATE)
                        )
                )
                .inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (view == null) view = new CountryDetailsView(getContainerView());
        presenter.attachView(view);
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(PRESENTER_STATE, presenter.saveState());
        super.onSaveInstanceState(outState);
    }

    private static final String PRESENTER_STATE = "presenter_state";

}
