package com.example.jeffr.popularmovieapp;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg_color_status));
        API_KEY = getString(R.string.THE_MOVIE_DB_API_TOKEN);
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
        switch(id){
            case R.id.toprated:
                PlaceholderFragment.fragments.get(0).resetLoader("toprated");
                break;
            case R.id.popular:
                PlaceholderFragment.fragments.get(0).resetLoader("toprated");
                break;
            case R.id.favorites:
                PlaceholderFragment.fragments.get(0).resetLoader("favorited");
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
