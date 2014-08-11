package com.findlyrics.db.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padonag on 04.08.2014.
 */
public class Artist {
    Long id;
    private String name;
    private List<Song> repertoir = new ArrayList<Song>();
    public Artist() {
    }
    public Artist(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;

        Artist artist = (Artist) o;

        if (!id.equals(artist.id)) return false;
        if (!name.equals(artist.name)) return false;

        return true;
    }

    public void addSong(Song song){
        this.repertoir.add(song);
    }

    public List<Song> getRepertoir() {
        return repertoir;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + repertoir.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                '}';
    }
}
