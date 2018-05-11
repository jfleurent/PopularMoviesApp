package com.example.jeffr.popularmovieapp;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.example.jeffr.popularmovieapp.Adapters.RecyclerViewOnClick;
import com.example.jeffr.popularmovieapp.Adapters.RecyclerviewAdapter;
import com.example.jeffr.popularmovieapp.Adapters.SectionsPagerAdapter;
import com.example.jeffr.popularmovieapp.DataObjects.Movie;
import com.example.jeffr.popularmovieapp.Utilities.JsonUtils;
import com.example.jeffr.popularmovieapp.Utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String API_KEY;
    private SectionsPagerAdapter mSectionsPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
      getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cardview_dark_background));
        API_KEY = getString(R.string.THE_MOVIE_DB_API_TOKEN);
        new FetchMovies().execute();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    public class FetchMovies extends AsyncTask<Boolean, Void, List<Movie>>{

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.INVISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(Boolean... booleans) {
            List<Movie> movieList = new ArrayList<>();
            URL movieUrl = NetworkUtils.buildPopularMoviesUrl(API_KEY);
            try {
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieUrl);
                movieList = JsonUtils.getMoviesList(MainActivity.this,jsonResponse);
            }
            catch (Exception e){

            }
            PlaceholderFragment.movieList = movieList;
            return movieList;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            PlaceholderFragment.movieList = movies;
            progressBar.setVisibility(View.INVISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
        }
    }


    public static class PlaceholderFragment extends Fragment implements RecyclerViewOnClick{

        private static final String ARG_SECTION_NUMBER = "section_number";

        @BindView(R.id.recyclerview)
        RecyclerView recyclerView;

        public static List<Movie> movieList;

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
            ButterKnife.bind(this,rootView);
            final FragmentActivity fragmentActivity = getActivity();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentActivity);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            if(movieList == null){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            recyclerView.setAdapter(new RecyclerviewAdapter(movieList,this));

            return rootView;
        }

        @Override
        public void rowSelected(int row) {

        }
    }


}
