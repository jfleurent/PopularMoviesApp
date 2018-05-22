package com.example.jeffr.popularmovieapp.utilities;

import com.example.jeffr.popularmovieapp.dataobjects.Movie;
import com.example.jeffr.popularmovieapp.dataobjects.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Movie> getMoviesList(List<String> jsonResponse){
        List<Movie> movies = new ArrayList<>();
        try{
            for(String jsonString : jsonResponse){
                ObjectMapper objectMapper = new ObjectMapper();
                TypeFactory typeFactory = objectMapper.getTypeFactory();
                JSONObject jsonObject = new JSONObject(jsonString);
                List<Movie> movies1 = objectMapper.readValue(jsonObject.getJSONArray("results")
                        .toString(),typeFactory.constructCollectionType(List.class, Movie.class));
                movies.addAll(movies1);
            }
            return movies;
        }catch (Exception e){
            return null;
        }
    }

    public static List<Review> getReviewsList(String jsonResponse){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            JSONObject jsonObject = new JSONObject(jsonResponse);
            return objectMapper.readValue(jsonObject.getJSONArray("results")
                    .toString(),typeFactory.constructCollectionType(List.class, Review.class));
        }catch (Exception e){
            return null;
        }
    }

    public static String getTrailerKey(String jsonResponse){
        try{
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            return jsonArray.getJSONObject(0).getString("key");
        }catch (Exception e){
            return null;
        }
    }
}
