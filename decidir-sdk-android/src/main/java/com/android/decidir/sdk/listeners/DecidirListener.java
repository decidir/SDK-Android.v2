package com.android.decidir.sdk.listeners;

/**
 * Created by biandra on 04/08/16.
 */
public interface DecidirListener <T> {
    public void getResponse(T response);
    public void getFailure(Throwable t);
}
