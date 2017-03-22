package com.davidarmbrust.spi.domain;

import java.util.Arrays;
import java.util.List;

/**
 * Provides container for Album information
 */
public class Album {
    private String id;
    private String name;

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

    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", artists=" + Arrays.toString(artists) +
                '}';
    }
}
