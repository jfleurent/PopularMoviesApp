package com.example.jeffr.popularmovieapp.utilities;

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
    private static final String VIDEO = "https://api.themoviedb.org/3/movie/";
    private static final String REVIEW = "https://api.themoviedb.org/3/movie/";
    private static final String REVIEW_API = "/reviews?api_key=";
    private static final String VIDEO_LANGUAGE = "&language=en-US";
    private final static String LANGUAGE_PAGES = "&language=en-US&page=";

    public static URL buildTopRatedMoviesUrl(String api_key, int pages) {
        String query = TOP_RATED+api_key+LANGUAGE_PAGES+pages;

        URL url = null;
        try {
            url = new URL(query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildReviewsUrl(String api_key,int movieID, int pages) {
        String query = REVIEW+movieID+REVIEW_API+api_key+LANGUAGE_PAGES+pages;

        URL url = null;
        try {
            url = new URL(query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildVideoUrl(String api_key,int id, int pages) {
        String query = VIDEO+id+"/videos?api_key="+api_key+VIDEO_LANGUAGE+pages;

        URL url = null;
        try {
            url = new URL(query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildPopularMoviesUrl(String api_key, int pages) {
        String query = POPULAR+api_key+LANGUAGE_PAGES+pages;

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
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(10000);
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
