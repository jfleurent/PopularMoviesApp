package com.example.jeffr.popularmovieapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;

    public MovieDBHelper(Context context,String database) {
        super(context, database, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieDBContract.MovieEntry.TABLE_NAME +
                " (" + MovieDBContract.MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY, "
                + MovieDBContract.MovieEntry.COLUMN_TITLE +" TEXT NOT NULL, "+
                MovieDBContract.MovieEntry.COLUMN_DATE +" TEXT NOT NULL, "+
                MovieDBContract.MovieEntry.COLUMN_OVERVIEW +" TEXT NOT NULL, "+
                MovieDBContract.MovieEntry.COLUMN_POSTER_PATH +" TEXT NOT NULL, "+
                MovieDBContract.MovieEntry.COLUMN_VOTE_AVERAGE +" REAL NOT NULL, "+
                MovieDBContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, "+
                MovieDBContract.MovieEntry.COLUMN_FAVORITE +" INTEGER NOT NULL );";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieDBContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
