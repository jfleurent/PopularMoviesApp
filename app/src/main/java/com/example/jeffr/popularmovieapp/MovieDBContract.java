package com.example.jeffr.popularmovieapp;

import android.provider.BaseColumns;

public class MovieDBContract {

    public class MovieEntry implements BaseColumns{

        public static final String TABLE_NAME = "movieTable";
        public static final String COLUMN_TITLE = "originalTitle";
        public static final String COLUMN_DATE = "releaseDate";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_ID = "movie_id";
        public static final String COLUMN_FAVORITE = "favorite";


    }
}
