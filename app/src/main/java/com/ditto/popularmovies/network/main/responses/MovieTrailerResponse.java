package com.ditto.popularmovies.network.main.responses;

import com.ditto.popularmovies.models.Movie;
import com.ditto.popularmovies.models.Video;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieTrailerResponse extends CommonResp{

    @Expose
    @SerializedName(("videos"))
    private VideoPayload videoPayload;

    public class VideoPayload {

        @Expose
        @SerializedName("results")
        private
        List<Video> videos;

    }

    public List<Video> getVideos() {

        if(videoPayload != null)
            return videoPayload.videos;

        return null;
    }

}
