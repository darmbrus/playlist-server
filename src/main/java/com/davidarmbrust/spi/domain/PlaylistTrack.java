package com.davidarmbrust.spi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Administrator on 3/27/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistTrack {
    private Track track;

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}
