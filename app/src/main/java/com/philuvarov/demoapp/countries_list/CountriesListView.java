package com.philuvarov.demoapp.countries_list;


import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.philuvarov.demoapp.R;
import com.philuvarov.demoapp.countries_list.adapter.CountriesAdapter;
import com.philuvarov.demoapp.countries_list.adapter.CountriesAdapter.CountryClickListener;
import com.philuvarov.demoapp.countries_list.adapter.CountryItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class CountriesListView {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @NonNull private final CountriesAdapter adapter;

    public CountriesListView(@NonNull View root,
                             @NonNull CountryClickListener listener) {
        ButterKnife.bind(this, root);
        adapter = new CountriesAdapter(listener);
        setupRecycler();
        refreshLayout.setEnabled(false);
        refreshLayout.setProgressViewOffset(true,
                root.getResources().getDimensionPixelSize(R.dimen.pull_refresh_offset_start),
                root.getResources().getDimensionPixelSize(R.dimen.pull_refresh_offset_end));

    }

    private void setupRecycler() {
        adapter.setHasStableIds(true);
        LinearLayoutManager lm = new LinearLayoutManager(recycler.getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);
    }

    public void showError() {
        Snackbar.make(recycler, R.string.error, LENGTH_SHORT).show();
    }

    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    public void setData(@NonNull List<CountryItem> data) {
        adapter.setCountries(data);
        adapter.notifyDataSetChanged();
    }
}
