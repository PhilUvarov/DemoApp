package com.philuvarov.demoapp.util;

import android.support.annotation.NonNull;

public interface Converter<T, R> {

    @NonNull
    R convert(@NonNull T value);

}
