package com.example.jeffr.popularmovieapp.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Movie implements Parcelable {
    private String original_title;
    private String release_date;
    private String poster_path;
    private float vote_average;
    private String overview;
    private int id;
    private String trailerKey;
    private boolean favorited;
    private float popularity;

    public Movie(String original_title, String release_date,
                 String poster_path, float vote_average, String overview,
                 int id, boolean favorited, float popularity) {
        this.original_title = original_title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.overview = overview;
        this.id = id;
        this.favorited = favorited;
        this.popularity = popularity;

    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVote_average() {

        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {

        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {

        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {

        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }


    public Movie() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.original_title);
        dest.writeString(this.release_date);
        dest.writeString(this.poster_path);
        dest.writeFloat(this.vote_average);
        dest.writeString(this.overview);
        dest.writeInt(this.id);
        dest.writeString(this.trailerKey);
        dest.writeByte(this.favorited ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.popularity);
    }

    protected Movie(Parcel in) {
        this.original_title = in.readString();
        this.release_date = in.readString();
        this.poster_path = in.readString();
        this.vote_average = in.readFloat();
        this.overview = in.readString();
        this.id = in.readInt();
        this.trailerKey = in.readString();
        this.favorited = in.readByte() != 0;
        this.popularity = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
