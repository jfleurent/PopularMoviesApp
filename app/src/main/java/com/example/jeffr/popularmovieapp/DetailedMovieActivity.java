package com.example.jeffr.popularmovieapp;

import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jeffr.popularmovieapp.dataobjects.Movie;
import com.example.jeffr.popularmovieapp.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

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

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    private final String API_KEY = getString(R.string.THE_MOVIE_DB_API_TOKEN);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);
        ButterKnife.bind(this);
        final Movie movie = getIntent().getParcelableExtra("Movie");
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500/"+movie.getPoster_path())
                .into(moviePoster);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg_color_status));
        movieDate.setText(movie.getRelease_date());
        movieOverview.setText(movie.getOverview());
        movieTitle.setText(movie.getOriginal_title());
        movieVotes.setText(String.valueOf(movie.getVote_average()));

        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchTrailer().execute(movie.getId());
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    class FetchTrailer extends AsyncTask<Integer,Void,String>{
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            URL trailerLink = NetworkUtils.buildVideoUrl(API_KEY,integers[0]);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}
