package com.philuvarov.demoapp.countries_list.adapter;


import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philuvarov.demoapp.R;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {

    public interface CountryClickListener {

        void onCountryClicked(@NonNull String code);

    }

    @NonNull
    private List<CountryItem> countries = new ArrayList<>();

    @NonNull
    private final CountryClickListener listener;

    public CountriesAdapter(@NonNull CountryClickListener listener) {
        this.listener = listener;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CountryViewHolder((TextView) inflateView(parent, R.layout.country_item));
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        holder.bind(countries.get(position), listener);
    }

    @NonNull
    private View inflateView(ViewGroup parent, @LayoutRes int resId) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    @Override
    public long getItemId(int position) {
        return getName(position).hashCode();
    }

    @NonNull
    private String getName(int position) {
        return countries.get(position).name;
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setCountries(@NonNull List<CountryItem> countries) {
        this.countries = countries;
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {

        TextView countryName;

        CountryViewHolder(@NonNull TextView view) {
            super(view);
            countryName = view;
        }

        private void bind(@NonNull CountryItem item, @NonNull CountryClickListener listener) {
            countryName.setText(item.name);
            countryName.setOnClickListener(v -> listener.onCountryClicked(item.code));
        }

    }

}

