package com.example.jeffr.popularmovieapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.content.CursorLoader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeffr.popularmovieapp.adapters.RecyclerViewOnClick;
import com.example.jeffr.popularmovieapp.adapters.RecyclerviewAdapter;
import com.example.jeffr.popularmovieapp.data.MovieDBContract;
import com.example.jeffr.popularmovieapp.dataobjects.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;


public class PlaceholderFragment extends Fragment implements RecyclerViewOnClick, android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>{

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static final int SECTION1_LOADER = 57;
    public static final int SECTION2_LOADER = 949;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    public static List<PlaceholderFragment> fragments = new ArrayList<>();
    static int sectionNumber = 0;

    public static Bundle bundle;

    public  Cursor cursor;
    public static int rowSelected;

    public PlaceholderFragment() {
    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        final FragmentActivity fragmentActivity = getActivity();
        GridLayoutManager linearLayoutManager = new GridLayoutManager(fragmentActivity,2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new RecyclerviewAdapter(this));

        if(bundle != null){
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 0){
                getActivity().getSupportLoaderManager().restartLoader(SECTION1_LOADER,bundle,this);
            }
            else{
                getActivity().getSupportLoaderManager().restartLoader(SECTION2_LOADER,bundle,this);
            }
        }
        else{
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 0){
                getActivity().getSupportLoaderManager().initLoader(SECTION1_LOADER, null, this);
            }
            else{
                getActivity().getSupportLoaderManager().initLoader(SECTION2_LOADER, null, this);
            }
        }
        return rootView;
    }

    @Override
    public void rowSelected(int row) {
        rowSelected = row;
        cursor.moveToPosition(row);
        String orginalTitle = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_TITLE));
        String releaseDate = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_DATE));
        String posterPath = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_POSTER_PATH));
        float voteAverage = cursor.getFloat(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_VOTE_AVERAGE));
        String overview = cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_OVERVIEW));
        int id = cursor.getInt(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_ID));
        float popularity = cursor.getFloat(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_POPULARITY));
        boolean favorited = cursor.getInt(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_FAVORITE)) == 1;
        Intent intent = new Intent(getActivity(), DetailedMovieActivity.class);
        intent.putExtra("Movie",new Movie(orginalTitle,releaseDate,posterPath,voteAverage,overview,id,favorited,popularity));
        intent.putExtra(ARG_SECTION_NUMBER,sectionNumber);
        startActivity(intent);
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int loaderId, @Nullable Bundle args) {
        String orderBy;
        String where;
        switch (loaderId) {
            case SECTION1_LOADER:
                Uri forecastQueryUri = MovieDBContract.MovieEntry.POPULAR_CONTENT_URI;
                Log.v(TAG, forecastQueryUri.toString());
                return new CursorLoader(getActivity(),
                        forecastQueryUri,
                        null,
                         where = args != null ? args.getString("Where"): null,
                        null,
                        orderBy = args != null ? args.getString("OrderBy"): null);
            case SECTION2_LOADER:
                Uri forecastQueryUri2 = MovieDBContract.MovieEntry.TOP_RATED_CONTENT_URI;
                Log.v(TAG, forecastQueryUri2.toString());
                return new CursorLoader(getActivity(),
                        forecastQueryUri2,
                        null,
                         where = args != null ? args.getString("Where"): null,
                        null,
                        orderBy = args != null ? args.getString("OrderBy"): null);
            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);

    }
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
        this.cursor = cursor;
        RecyclerviewAdapter recyclerviewAdapter = new RecyclerviewAdapter(this);
        recyclerviewAdapter.setCursor(cursor);
        recyclerView.setAdapter(recyclerviewAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {

    }

}