package com.davidarmbrust.spi.domain;

import java.util.Arrays;

/**
 * Provides container for Album information
 */
public class Album {
    private String id;
    private String name;
    private Paging tracks;

    public Artist[] getArtists() {
        return artists;
    }

    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

    private Artist[] artists;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Paging getTracks() {
        return tracks;
    }

    public void setTracks(Paging tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", artists=" + Arrays.toString(artists) +
                '}';
    }
}
