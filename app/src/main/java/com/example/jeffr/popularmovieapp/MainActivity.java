package com.example.jeffr.popularmovieapp;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;


import com.example.jeffr.popularmovieapp.adapters.SectionsPagerAdapter;
import com.example.jeffr.popularmovieapp.data.MovieDBContract;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

   static ViewPager mViewPager;

   static ProgressBar progressBar;

   static Context mainActivityContext;

   static TabLayout tabLayout;

   static FragmentManager fragmentManager;

    @BindView(R.id.toolbar2)
    Toolbar toolbar;

    public static String API_KEY;
    public static  SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg_color_status));
        API_KEY = getString(R.string.THE_MOVIE_DB_API_TOKEN);
        toolbar.inflateMenu(R.menu.menu);
        mainActivityContext = this;
        fragmentManager = getSupportFragmentManager();
        tabLayout = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.container);
        progressBar = findViewById(R.id.progressbar);
        new FetchMovies().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = null;
        switch(id){
            case R.id.toprated:
                bundle = new Bundle();
                bundle.putString("OrderBy", MovieDBContract.MovieEntry.COLUMN_VOTE_AVERAGE + " DESC");
                PlaceholderFragment.bundle = bundle;
                fragmentManager.beginTransaction().detach(PlaceholderFragment.fragments
                        .get(0)).attach(PlaceholderFragment.fragments.get(0)).commit();
                fragmentManager.beginTransaction().detach(PlaceholderFragment.fragments
                        .get(1)).attach(PlaceholderFragment.fragments.get(1)).commit();
                break;
            case R.id.popular:
                bundle = new Bundle();
                bundle.putString("OrderBy", MovieDBContract.MovieEntry.COLUMN_POPULARITY + " DESC");
                PlaceholderFragment.bundle = bundle;
                fragmentManager.beginTransaction().detach(PlaceholderFragment.fragments
                        .get(0)).attach(PlaceholderFragment.fragments.get(0)).commit();
                fragmentManager.beginTransaction().detach(PlaceholderFragment.fragments
                        .get(1)).attach(PlaceholderFragment.fragments.get(1)).commit();
                break;
            case R.id.favorites:
                bundle = new Bundle();
                bundle.putString("OrderBy", MovieDBContract.MovieEntry.COLUMN_FAVORITE + " DESC");
                bundle.putString("Where", MovieDBContract.MovieEntry.COLUMN_FAVORITE +" = " + 1);
                PlaceholderFragment.bundle = bundle;
                fragmentManager.beginTransaction().detach(PlaceholderFragment.fragments
                        .get(0)).attach(PlaceholderFragment.fragments.get(0)).commit();
                fragmentManager.beginTransaction().detach(PlaceholderFragment.fragments
                        .get(1)).attach(PlaceholderFragment.fragments.get(1)).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
