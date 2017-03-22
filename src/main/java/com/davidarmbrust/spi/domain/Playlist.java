package com.davidarmbrust.spi.domain;

/**
 * Created by Administrator on 3/20/2017.
 */
public class Playlist {
    private String href;
    private String id;
    private String name;
    private PlaylistTrack[] tracks;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlaylistTrack[] getTracks() {
        return tracks;
    }

    public void setTracks(PlaylistTrack[] tracks) {
        this.tracks = tracks;
    }
}
