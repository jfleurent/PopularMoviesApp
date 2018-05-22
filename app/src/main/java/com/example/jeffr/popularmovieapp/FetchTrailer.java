package com.example.jeffr.popularmovieapp;

import android.os.AsyncTask;

import com.example.jeffr.popularmovieapp.utilities.JsonUtils;
import com.example.jeffr.popularmovieapp.utilities.NetworkUtils;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.io.IOException;
import java.net.URL;

import static com.example.jeffr.popularmovieapp.DetailedMovieActivity.activity;
import static com.example.jeffr.popularmovieapp.DetailedMovieActivity.context;
import static com.example.jeffr.popularmovieapp.DetailedMovieActivity.API_KEY;
import static com.example.jeffr.popularmovieapp.DetailedMovieActivity.API_KEY2;
import static com.example.jeffr.popularmovieapp.DetailedMovieActivity.movie;

class FetchTrailer extends AsyncTask<Integer, Void, String> {

    @Override
    protected String doInBackground(Integer... integers) {
        URL trailerLink = NetworkUtils.buildVideoUrl(API_KEY, integers[0],1);
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
        final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context);

        if (result != YouTubeInitializationResult.SUCCESS) {
            result.getErrorDialog(activity, 0).show();
        }
        if (YouTubeIntents.canResolvePlayVideoIntentWithOptions(context)) {
            context.startActivity(YouTubeStandalonePlayer.createVideoIntent(activity,
                    API_KEY2, s, 0, true, true));
        }
    }
}
