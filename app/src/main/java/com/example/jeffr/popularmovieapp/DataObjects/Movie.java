package com.example.jeffr.popularmovieapp.DataObjects;
//title, release date, movie poster, vote average, and plot synopsis.
public class Movie {
    private String title;
    private String releaseDate;
    private int moviePoster;
    private double voteAverage;
    private String synopsis;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getVoteAverage() {

        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getMoviePoster() {

        return moviePoster;
    }

    public void setMoviePoster(int moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getReleaseDate() {

        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
