package com.example.jeffr.popularmovieapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeffr.popularmovieapp.data.MovieDBContract;
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

    @BindView(R.id.trailer_button)
    Button trailerButton;

    @BindView(R.id.favorite_button)
    ImageButton favoriteButton;

    @BindView(R.id.movie_content_scrollview)
    ScrollView scrollView;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    static RecyclerView reviewsRecyclerView;
    static ProgressBar progressBar;
    static Movie movie;
    static Activity activity;
    public static Context context;
    public static  String API_KEY;
    public static  String API_KEY2;

    Cursor cursor;
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);
        ButterKnife.bind(this);

        movie = getIntent().getParcelableExtra("Movie");
        API_KEY = getString(R.string.THE_MOVIE_DB_API_TOKEN);
        API_KEY2 = getString(R.string.YOUTUBE_API_KEY);
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + movie.getPoster_path())
                .into(moviePoster);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg_color_status));

        movieDate.setText(movie.getRelease_date());
        movieOverview.setText(movie.getOverview());
        movieTitle.setText(movie.getOriginal_title());
        movieVotes.setText(String.valueOf(movie.getVote_average()));

        scrollView.smoothScrollTo(0, 20);
        scrollView.smoothScrollBy(0, 20);

        reviewsRecyclerView = findViewById(R.id.reviews_recyclerview);
        progressBar = findViewById(R.id.progressbar);
        context = this;
        activity = this;

        if (movie.isFavorited()) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_on);
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite);
        }

        new FetchReview().execute(movie.getId());

        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchTrailer().execute(movie.getId());
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatabase();
            }
        });
    }

    public void setFavoriteButton(Boolean favorite) {
        if (favorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_on);
            Toast.makeText(DetailedMovieActivity.this, "Movie Favorited", Toast.LENGTH_SHORT).show();
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite);
            Toast.makeText(DetailedMovieActivity.this, "Movie Unfavorited", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDatabase(){

        int sectionNumber = getIntent().getExtras().getInt("section_number");

        ContentValues cv = new ContentValues();
        cv.put(MovieDBContract.MovieEntry.COLUMN_DATE, movie.getRelease_date());
        cv.put(MovieDBContract.MovieEntry.COLUMN_FAVORITE, !movie.isFavorited());
        cv.put(MovieDBContract.MovieEntry.COLUMN_ID, movie.getId());
        cv.put(MovieDBContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        cv.put(MovieDBContract.MovieEntry.COLUMN_TITLE, movie.getOriginal_title());
        cv.put(MovieDBContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPoster_path());
        cv.put(MovieDBContract.MovieEntry.COLUMN_POPULARITY,movie.getPopularity());
        cv.put(MovieDBContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVote_average());

        switch (sectionNumber) {
            case 0:
                Uri forecastQueryUri = MovieDBContract.MovieEntry.POPULAR_CONTENT_URI;
                getContentResolver().update(forecastQueryUri,
                        cv,
                        MovieDBContract.MovieEntry.COLUMN_ID + " = " + movie.getId()
                        , null);
                movie.setFavorited(!movie.isFavorited());
                setFavoriteButton(movie.isFavorited());
                break;

            case 1:
                Uri forecastQueryUri2 = MovieDBContract.MovieEntry.TOP_RATED_CONTENT_URI;
                getContentResolver().update(forecastQueryUri2,
                        cv,
                        MovieDBContract.MovieEntry.COLUMN_ID + " = " + movie.getId()
                        , null);
                movie.setFavorited(!movie.isFavorited());
                setFavoriteButton(movie.isFavorited());

                break;

        }
    }



}
