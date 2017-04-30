package com.philuvarov.demoapp.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.philuvarov.demoapp.DemoApp;
import com.philuvarov.demoapp.di.component.AppComponent;

public abstract class BaseActivity extends AppCompatActivity {

    protected View getContainerView() {
        return findViewById(getContainerViewId());
    }

    @NonNull
    protected AppComponent getAppComponent() {
        return ((DemoApp) getApplication()).component();
    }

    @IdRes
    private int getContainerViewId() {
        return android.R.id.content;
    }

    @Nullable
    protected Parcelable getParcelable(@Nullable Bundle state, @NonNull String key) {
        return state != null ? state.getParcelable(key) : null;
    }
}
