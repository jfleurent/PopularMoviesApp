package com.example.jeffr.popularmovieapp.DataObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieResultsDataHolder {
    @JsonProperty("results")
    public List<Movie> movieList;
}
