package com.philuvarov.demoapp.country_details;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.philuvarov.demoapp.R;
import com.philuvarov.demoapp.util.SvgDecoder;
import com.philuvarov.demoapp.util.SvgDrawableTranscoder;
import com.philuvarov.demoapp.util.SvgSoftwareLayerSetter;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;
import static com.bumptech.glide.Glide.with;

public class CountryDetailsView {

    @BindView(R.id.flag)
    ImageView flag;
    @BindView(R.id.capital_value)
    TextView capital;
    @BindView(R.id.population_value)
    TextView population;
    @BindView(R.id.title)
    TextView countryName;


    @NonNull
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public CountryDetailsView(@NonNull View containerView) {
        ButterKnife.bind(this, containerView);
        requestBuilder =
                with(flag.getContext())
                        .using(Glide.buildStreamModelLoader(Uri.class, flag.getContext()), InputStream.class)
                        .from(Uri.class)
                        .as(SVG.class)
                        .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                        .sourceEncoder(new StreamEncoder())
                        .cacheDecoder(new FileToStreamDecoder<>(new SvgDecoder()))
                        .decoder(new SvgDecoder())
                        .listener(new SvgSoftwareLayerSetter());
    }

    public void showError() {
        Snackbar.make(flag, R.string.error, LENGTH_SHORT).show();
    }

    public void bindData(@NonNull CountryDetails details) {
        flag.post(() -> {
            Uri uri = Uri.parse(details.flagUrl);
            requestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(uri)
                    .into(flag);
        });

        countryName.setText(details.countryName);
        capital.setText(details.capital);
        population.setText(details.population);
    }
}
