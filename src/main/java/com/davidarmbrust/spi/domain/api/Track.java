package com.davidarmbrust.spi.domain.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

/**
 * Provides container for Track information.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
    private Album album;
    private Artist[] artists;
    private String id;
    private String href;
    private String name;
    private String uri;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist[] getArtists() {
        return artists;
    }

    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Track{" +
                "album=" + album +
                ", artists=" + Arrays.toString(artists) +
                ", id='" + id + '\'' +
                ", href='" + href + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
