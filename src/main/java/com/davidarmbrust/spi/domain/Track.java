package com.davidarmbrust.spi.domain;

/**
 * Created by Administrator on 3/20/2017.
 */
public class Track {
    private Album album;
    private Artist[] artists;
    private String id;
    private String href;

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
}
