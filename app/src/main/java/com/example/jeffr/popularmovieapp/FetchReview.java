package com.example.jeffr.popularmovieapp;

import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.jeffr.popularmovieapp.adapters.ReviewsRecyclerViewAdapter;
import com.example.jeffr.popularmovieapp.dataobjects.Review;
import com.example.jeffr.popularmovieapp.utilities.JsonUtils;
import com.example.jeffr.popularmovieapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.example.jeffr.popularmovieapp.DetailedMovieActivity.context;
import static com.example.jeffr.popularmovieapp.DetailedMovieActivity.progressBar;
import static com.example.jeffr.popularmovieapp.DetailedMovieActivity.reviewsRecyclerView;
import static com.example.jeffr.popularmovieapp.MainActivity.API_KEY;

class FetchReview extends AsyncTask<Integer, Void, List<Review>> {

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Review> doInBackground(Integer... integers) {
        URL reviewsLink = NetworkUtils.buildReviewsUrl(API_KEY, integers[0],1);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        reviewsRecyclerView.setLayoutManager(linearLayoutManager);
        reviewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reviewsRecyclerView.setAdapter(new ReviewsRecyclerViewAdapter(reviews));
    }


}