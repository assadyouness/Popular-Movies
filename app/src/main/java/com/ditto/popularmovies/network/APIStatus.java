package com.ditto.popularmovies.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class APIStatus {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    boolean shouldShowErrorMessage = true;
    boolean isNetworkError = false;

    public APIStatus(@NonNull Status status, @Nullable String message, Boolean isNetworkError) {
        this.status = status;
        this.message = message;
        this.isNetworkError = isNetworkError;
    }

    public static APIStatus success() {
        return new APIStatus(Status.SUCCESS,null,false);
    }

    public static APIStatus error(@NonNull String msg, boolean isNetworkError) {
        return new APIStatus(Status.ERROR, msg,isNetworkError);
    }

    public static APIStatus loading() {
        return new APIStatus(Status.LOADING, null,false);
    }

    public enum Status { SUCCESS, ERROR, LOADING}

    @Nullable
    public String getMessage() {
        if(shouldShowErrorMessage) {
            shouldShowErrorMessage = false;
            return message;
        }
        else {
            return null;
        }
    }

    public boolean isNetworkError() {
        return isNetworkError;
    }
}