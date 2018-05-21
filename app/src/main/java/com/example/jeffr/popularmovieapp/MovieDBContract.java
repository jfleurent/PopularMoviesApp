package com.example.jeffr.popularmovieapp;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieDBContract {
    public static final String CONTENT_AUTHORITY = "com.example.jeffr.popularmovieapp";

    /*
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider for Sunshine.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_POPULAR_MOVIES = "popularmovies";

    public static final String PATH_TOP_RATED_MOVIES = "topratedmovies";

    public static class MovieEntry implements BaseColumns{

        public static final Uri POPULAR_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_POPULAR_MOVIES)
                .build();

        public static final Uri TOP_RATED_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TOP_RATED_MOVIES)
                .build();

        public static final String TABLE_NAME = "movieTable";
        public static final String COLUMN_TITLE = "originalTitle";
        public static final String COLUMN_DATE = "releaseDate";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_ID = "movie_id";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_FAVORITE = "favorite";


    }
}
