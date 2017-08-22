package com.android.decidir.sdk.services;

import com.android.decidir.sdk.exceptions.DecidirException;

public interface DecidirCallback<T> {
    void onSuccess(T response);

    void onFailure(DecidirException e);
}
