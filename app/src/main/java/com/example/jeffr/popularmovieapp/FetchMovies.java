package com.example.jeffr.popularmovieapp;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.example.jeffr.popularmovieapp.adapters.SectionsPagerAdapter;
import com.example.jeffr.popularmovieapp.dataobjects.Movie;
import com.example.jeffr.popularmovieapp.utilities.JsonUtils;
import com.example.jeffr.popularmovieapp.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.jeffr.popularmovieapp.MainActivity.API_KEY;
import static com.example.jeffr.popularmovieapp.MainActivity.fragmentManager;
import static com.example.jeffr.popularmovieapp.MainActivity.mSectionsPagerAdapter;
import static com.example.jeffr.popularmovieapp.MainActivity.progressBar;
import static com.example.jeffr.popularmovieapp.MainActivity.mViewPager;
import static com.example.jeffr.popularmovieapp.MainActivity.tabLayout;

public class FetchMovies extends AsyncTask<String, Void, List<List<Movie>>> {

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.INVISIBLE);
    }

    @Override
    protected List<List<Movie>> doInBackground(String... strings) {
        List<Movie> movieList;

        List<List<Movie>> movieLists = new ArrayList<>();
        URL movieUrl = NetworkUtils.buildPopularMoviesUrl(API_KEY);
        URL movieUrl2 = NetworkUtils.buildTopRatedMoviesUrl(API_KEY);
        try {

            String jsonResponse = NetworkUtils
                    .getResponseFromHttpUrl(movieUrl);

            movieList = JsonUtils.getMoviesList(jsonResponse);

            movieLists.add(movieList);

            String jsonResponse2 = NetworkUtils
                    .getResponseFromHttpUrl(movieUrl2);

            movieList = JsonUtils.getMoviesList(jsonResponse2);

            movieLists.add(movieList);

        } catch (Exception e) {
            Log.v(TAG, "Something went wrong");
        }
        return movieLists;
    }

    @Override
    protected void onPostExecute(List<List<Movie>> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
        for(Movie movie : movies.get(0)){
            ContentValues cv = new ContentValues();
            cv.put(MovieDBContract.MovieEntry.COLUMN_DATE,movie.getRelease_date());
            cv.put(MovieDBContract.MovieEntry.COLUMN_FAVORITE,movie.isFavorited());
            cv.put(MovieDBContract.MovieEntry.COLUMN_ID,movie.getId());
            cv.put(MovieDBContract.MovieEntry.COLUMN_OVERVIEW,movie.getOverview());
            cv.put(MovieDBContract.MovieEntry.COLUMN_TITLE,movie.getOriginal_title());
            cv.put(MovieDBContract.MovieEntry.COLUMN_POSTER_PATH,movie.getPoster_path());
            cv.put(MovieDBContract.MovieEntry.COLUMN_VOTE_AVERAGE,movie.getVote_average());
            MainActivity.mainActivityContext.getContentResolver().insert(
                    MovieDBContract.MovieEntry.POPULAR_CONTENT_URI,cv
            );
        }
        for(Movie movie : movies.get(1)){
            ContentValues cv = new ContentValues();
            cv.put(MovieDBContract.MovieEntry.COLUMN_DATE,movie.getRelease_date());
            cv.put(MovieDBContract.MovieEntry.COLUMN_FAVORITE,movie.isFavorited());
            cv.put(MovieDBContract.MovieEntry.COLUMN_ID,movie.getId());
            cv.put(MovieDBContract.MovieEntry.COLUMN_OVERVIEW,movie.getOverview());
            cv.put(MovieDBContract.MovieEntry.COLUMN_TITLE,movie.getOriginal_title());
            cv.put(MovieDBContract.MovieEntry.COLUMN_POSTER_PATH,movie.getPoster_path());
            cv.put(MovieDBContract.MovieEntry.COLUMN_VOTE_AVERAGE,movie.getVote_average());
            MainActivity.mainActivityContext.getContentResolver().insert(
                    MovieDBContract.MovieEntry.TOP_RATED_CONTENT_URI,cv
            );
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new MyPageChangeListener());
        tabLayout.addOnTabSelectedListener(new MyTabAdapter());

    }

    class MyTabAdapter implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
            if (tab.equals(tabLayout.getTabAt(0))) {
                PlaceholderFragment.sectionNumber = 0;
            } else {
                PlaceholderFragment.sectionNumber = 1;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    public class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            tabLayout.getTabAt(i).select();
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }
}
