package com.vrishankgupta.movies.TvShow;

import java.io.Serializable;

/**
 * Created by vrishankgupta on 08/01/18.
 */
//AIzaSyAVIMIkGnXbZKCTozbGpsbs7Q_chf_Wvh4
public class Tv implements Serializable{
    private String original_name;
    private String vote_average;
    private String first_air_date;
    private String overview;
    private String poster_path;

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }


    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
