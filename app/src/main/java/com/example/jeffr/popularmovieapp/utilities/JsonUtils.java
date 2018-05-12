package com.example.jeffr.popularmovieapp.utilities;

import com.example.jeffr.popularmovieapp.dataobjects.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.json.JSONObject;

import java.util.List;

public class JsonUtils {

    public static List<Movie> getMoviesList(String jsonResponse){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            JSONObject jsonObject = new JSONObject(jsonResponse);

            return objectMapper.readValue(jsonObject.getJSONArray("results")
                    .toString(),typeFactory.constructCollectionType(List.class, Movie.class));
        }catch (Exception e){
            return null;
        }

    }
}
