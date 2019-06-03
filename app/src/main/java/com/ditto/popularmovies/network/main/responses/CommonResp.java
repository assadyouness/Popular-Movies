package com.ditto.popularmovies.network.main.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonResp {

    @Expose
    @SerializedName(("status_code"))
    private int statusCode;

    @Expose
    @SerializedName(("status_message"))
    private String statusMessage;

    private boolean isError = false;

    private Throwable throwable;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setError(boolean error) {
        isError = error;
    }


    public boolean isError() {
        return isError;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
