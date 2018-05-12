package com.example.jeffr.popularmovieapp;

import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeffr.popularmovieapp.dataobjects.Movie;
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
        Movie movie = getIntent().getParcelableExtra("Movie");
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500/"+movie.getPoster_path())
                .into(moviePoster);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg_color_status));
        movieDate.setText(movie.getRelease_date());
        movieOverview.setText(movie.getOverview());
        movieTitle.setText(movie.getOriginal_title());
        movieVotes.setText(String.valueOf(movie.getVote_average()));
    }

}
