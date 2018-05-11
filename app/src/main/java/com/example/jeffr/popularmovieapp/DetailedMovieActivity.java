package com.example.jeffr.popularmovieapp;

import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedMovieActivity extends AppCompatActivity {

    @BindView(R.id.movie_poster_imageview)
    ImageView moviePoster;

    @BindView(R.id.movie_title_textview)
    TextView movieTitle;

    @BindView(R.id.movie_overview_textviewview)
    TextView movieOverview;

    @BindView(R.id.movie_date_textview)
    TextView movieDate;

    @BindView(R.id.movie_votes_textview)
    TextView movieVotes;


    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);
        ButterKnife.bind(this);
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500/"+getIntent().getExtras().getString("PosterPath"))
                .into(moviePoster);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        movieDate.setText(getIntent().getExtras().getString("Date"));
        movieOverview.setText(getIntent().getExtras().getString("Overview"));
        movieTitle.setText(getIntent().getExtras().getString("Title"));
        movieVotes.setText(String.valueOf(getIntent().getExtras().getFloat("Votes")));
    }

}
