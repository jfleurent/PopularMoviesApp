package com.example.jeffr.popularmovieapp.Utilities;

import android.content.Context;

import com.example.jeffr.popularmovieapp.DataObjects.Movie;
import com.example.jeffr.popularmovieapp.DataObjects.MovieResultsDataHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class JsonUtils {

    public static List<Movie> getMoviesList(Context context, String jsonResponse){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            JSONObject jsonObject = new JSONObject(jsonResponse);

            return objectMapper.readValue(jsonObject.getJSONArray("results").toString(),typeFactory.constructCollectionType(List.class, Movie.class));
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
            return null;
        }

    }
}
