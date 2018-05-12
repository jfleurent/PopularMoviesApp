package com.example.jeffr.popularmovieapp;

import android.content.Intent;
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


import com.example.jeffr.popularmovieapp.adapters.RecyclerViewOnClick;
import com.example.jeffr.popularmovieapp.adapters.RecyclerviewAdapter;
import com.example.jeffr.popularmovieapp.adapters.SectionsPagerAdapter;
import com.example.jeffr.popularmovieapp.dataobjects.Movie;
import com.example.jeffr.popularmovieapp.utilities.JsonUtils;
import com.example.jeffr.popularmovieapp.utilities.NetworkUtils;

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

    @BindView(R.id.toolbar2)
    Toolbar toolbar;

    private String API_KEY;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg_color_status));
        API_KEY = getString(R.string.THE_MOVIE_DB_API_TOKEN);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new MyPageChangeListener());
        tabLayout.addOnTabSelectedListener(new MyTabAdapter());
        new FetchMovies().execute();

    }

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

            }

            return movieLists;
        }

        @Override
        protected void onPostExecute(List<List<Movie>> movies) {
            progressBar.setVisibility(View.INVISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            PlaceholderFragment.movieLists = movies;
            PlaceholderFragment.fragments.get(0).resetRecyclerview();
            PlaceholderFragment.fragments.get(1).resetRecyclerview();
        }
    }


    public static class PlaceholderFragment extends Fragment implements RecyclerViewOnClick {

        private static final String ARG_SECTION_NUMBER = "section_number";

        @BindView(R.id.recyclerview)
        RecyclerView recyclerView;

        public static List<PlaceholderFragment> fragments = new ArrayList<>();
        static int sectionNumber = 0;
        public static List<List<Movie>> movieLists;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public void resetRecyclerview(){
            RecyclerviewAdapter recyclerviewAdapter = new RecyclerviewAdapter(this);
            recyclerviewAdapter.setMovieList(movieLists.get(getArguments().getInt(ARG_SECTION_NUMBER)));
            recyclerView.setAdapter(recyclerviewAdapter);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, rootView);
            final FragmentActivity fragmentActivity = getActivity();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentActivity);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(new RecyclerviewAdapter(this));
            return rootView;
        }

        @Override
        public void rowSelected(int row) {
            Movie selectedMovie = movieLists.get(sectionNumber).get(row);
            Intent intent = new Intent(getActivity(), DetailedMovieActivity.class);
            intent.putExtra("Movie",selectedMovie);
//            intent.putExtra("Title",selectedMovie.getOriginal_title());
//            intent.putExtra("Overview",selectedMovie.getOverview());
//            intent.putExtra("PosterPath",selectedMovie.getPoster_path());
//            intent.putExtra("Date",selectedMovie.getRelease_date());
//            intent.putExtra("Votes",selectedMovie.getVote_average());
            startActivity(intent);
        }
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
