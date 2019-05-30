package com.ditto.popularmovies.utlis;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class CommonUtils {

    public static URL buildImageUrl(String id, String size) {
        Uri builtUri = Uri.parse(Constants.image_url + size + id).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
