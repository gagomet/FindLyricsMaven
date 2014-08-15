package com.findlyrics.rest.model;

/**
 * Created by Padonag on 10.08.2014.
 */

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class SongPojo {

    @JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;
    @JsonProperty("artist")
    private ArtistPojo artist;

    public SongPojo(){}

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("artist")
    public ArtistPojo getArtist() {
        return artist;
    }
    @JsonProperty("artist")
    public void setArtist(ArtistPojo artist) {
        this.artist = artist;
    }



}


