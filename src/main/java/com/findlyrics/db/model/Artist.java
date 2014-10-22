package com.findlyrics.db.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padonag on 04.08.2014.
 */

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;
    @Column(name = "artist_name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id")
    private List<Song> repertoir = new ArrayList<Song>();


    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
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
        return id.equals(artist.id) && name.equals(artist.name);
    }

    public void addSong(Song song) {
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
