package com.ditto.popularmovies.network;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {

    private static final String MSG_NETWORK_ERROR = "No_Internet";
    private static final String MSG_UNEXPECTED_ERROR = "Unexpected_Error";

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.isSuccessful() && response.body() != null) {

            success(response.body(), response);

        } else {
            failure(false, response.body(), MSG_UNEXPECTED_ERROR, response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof IOException) {
            failure(true, MSG_NETWORK_ERROR, MSG_NETWORK_ERROR);
        } else {
            failure(false, MSG_UNEXPECTED_ERROR, t.getLocalizedMessage());
        }
    }

    private void failure(boolean io, String message, String error) {
        failure(io, null, message, error);
    }

    public abstract void success(T t, Response<T> response);

    public abstract void failure(boolean io, T t, String message, String error);
}
