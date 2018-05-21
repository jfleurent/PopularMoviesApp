package com.example.jeffr.popularmovieapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MovieContentProvider extends ContentProvider {


    public static final int CODE_POPULAR_MOVIE = 100;
    public static final int CODE_TOP_RATED_MOVIE = 101;

    private static final String POPULAR_DATABASE_NAME = "popular_movies.db";
    private static final String TOP_RATED_DATABASE_NAME = "toprated_movies.db";


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MovieDBHelper mOpenHelper1;
    private MovieDBHelper mOpenHelper2;



    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieDBContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieDBContract.PATH_POPULAR_MOVIES, CODE_POPULAR_MOVIE);

        matcher.addURI(authority, MovieDBContract.PATH_TOP_RATED_MOVIES, CODE_TOP_RATED_MOVIE);

        return matcher;
    }



    @Override
    public boolean onCreate() {
        mOpenHelper1 = new MovieDBHelper(getContext(),POPULAR_DATABASE_NAME);
        mOpenHelper2 = new MovieDBHelper(getContext(),TOP_RATED_DATABASE_NAME);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            case CODE_POPULAR_MOVIE:
            {
                cursor = mOpenHelper1.getReadableDatabase().query(
                        MovieDBContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_TOP_RATED_MOVIE:
                cursor = mOpenHelper2.getReadableDatabase().query(
                        MovieDBContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new RuntimeException("Unknown uri: " + uri);        }

            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase popularDB = mOpenHelper1.getWritableDatabase();
        final SQLiteDatabase topratedDB = mOpenHelper2.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {

            case CODE_POPULAR_MOVIE:
                popularDB.beginTransaction();
                try {
                   popularDB.insert(MovieDBContract.MovieEntry.TABLE_NAME,null,contentValues);
                    popularDB.setTransactionSuccessful();
                }
                catch (Exception e){

                }
                finally {
                    popularDB.endTransaction();
                }
                break;
            case CODE_TOP_RATED_MOVIE:
                topratedDB.beginTransaction();
                try {
                    topratedDB.insert(MovieDBContract.MovieEntry.TABLE_NAME,null,contentValues);
                    topratedDB.setTransactionSuccessful();
                }
                catch (Exception e){

                }
                finally {
                    topratedDB.endTransaction();
                }
                break;
            default:
                throw new RuntimeException("Unknown uri: " + uri);

        }
            return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        selection = selection == null ? "1" : selection;

        switch (sUriMatcher.match(uri)) {

            case CODE_POPULAR_MOVIE:
                numRowsDeleted = mOpenHelper1.getWritableDatabase().delete(
                        MovieDBContract.MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;
            case CODE_TOP_RATED_MOVIE:
                numRowsDeleted = mOpenHelper2.getWritableDatabase().delete(
                        MovieDBContract.MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsUpdated;
        selection = selection == null ? "1" : selection;

        switch (sUriMatcher.match(uri)) {

            case CODE_POPULAR_MOVIE:
                numRowsUpdated = mOpenHelper1.getWritableDatabase().updateWithOnConflict(
                        MovieDBContract.MovieEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs,SQLiteDatabase.CONFLICT_IGNORE);

                break;
            case CODE_TOP_RATED_MOVIE:
                numRowsUpdated = mOpenHelper2.getWritableDatabase().updateWithOnConflict(
                        MovieDBContract.MovieEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs,SQLiteDatabase.CONFLICT_IGNORE);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsUpdated;
    }
}
