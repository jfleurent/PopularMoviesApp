package com.example.jeffr.popularmovieapp.Utilities;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

public class NetworkUtils {
    private final static String TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
    private static final String POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key=";
    private final static String LANGUAGE_PAGES = "&language=en-US&page=5";


    public static URL buildTopRatedMoviesUrl(String api_key) {
        String query = TOP_RATED+api_key+LANGUAGE_PAGES;

        URL url = null;
        try {
            url = new URL(query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildPopularMoviesUrl(String api_key) {
        String query = POPULAR+api_key+LANGUAGE_PAGES;

        URL url = null;
        try {
            url = new URL(query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
