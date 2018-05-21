package com.example.jeffr.popularmovieapp;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeffr.popularmovieapp.adapters.RecyclerViewOnClick;
import com.example.jeffr.popularmovieapp.adapters.RecyclerviewAdapter;
import com.example.jeffr.popularmovieapp.adapters.ReviewsRecyclerViewAdapter;
import com.example.jeffr.popularmovieapp.dataobjects.Movie;
import com.example.jeffr.popularmovieapp.dataobjects.Review;
import com.example.jeffr.popularmovieapp.utilities.JsonUtils;
import com.example.jeffr.popularmovieapp.utilities.NetworkUtils;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static com.example.jeffr.popularmovieapp.PlaceholderFragment.SECTION1_LOADER;
import static com.example.jeffr.popularmovieapp.PlaceholderFragment.SECTION2_LOADER;

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

    @BindView(R.id.reviews_recyclerview)
    RecyclerView reviewsRecyclerView;

    @BindView(R.id.movie_content_scrollview)
    ScrollView scrollView;

    Movie movie;
    Cursor cursor;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private String API_KEY;
    private String API_KEY2;


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

    class FetchTrailer extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            URL trailerLink = NetworkUtils.buildVideoUrl(API_KEY, integers[0]);
            try {
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(trailerLink);
                movie.setTrailerKey(JsonUtils.getTrailerKey(jsonResponse));
                return JsonUtils.getTrailerKey(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            movie.setTrailerKey(s);
            final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(DetailedMovieActivity.this);

            if (result != YouTubeInitializationResult.SUCCESS) {
                result.getErrorDialog(DetailedMovieActivity.this, 0).show();
            }
            if (YouTubeIntents.canResolvePlayVideoIntentWithOptions(DetailedMovieActivity.this)) {
                startActivity(YouTubeStandalonePlayer.createVideoIntent(DetailedMovieActivity.this,
                        API_KEY2, s, 0, true, true));
            }
        }
    }

    class FetchReview extends AsyncTask<Integer, Void, List<Review>> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Review> doInBackground(Integer... integers) {
            URL reviewsLink = NetworkUtils.buildReviewsUrl(API_KEY, integers[0]);
            try {
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(reviewsLink);
                return JsonUtils.getReviewsList(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            progressBar.setVisibility(View.INVISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailedMovieActivity.this);
            reviewsRecyclerView.setLayoutManager(linearLayoutManager);
            reviewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            reviewsRecyclerView.setAdapter(new ReviewsRecyclerViewAdapter(reviews));
        }


    }
}
