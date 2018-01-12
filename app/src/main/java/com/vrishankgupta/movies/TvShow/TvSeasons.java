package com.vrishankgupta.movies.TvShow;

import java.io.Serializable;

/**
 * Created by vrishankgupta on 11/01/18.
 */

public class TvSeasons implements Serializable {
    private String id;
    private String episodes;
    private String seasonNo;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getSeasonNo() {
        return seasonNo;
    }

    public void setSeasonNo(String seasonNo) {
        this.seasonNo = seasonNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
