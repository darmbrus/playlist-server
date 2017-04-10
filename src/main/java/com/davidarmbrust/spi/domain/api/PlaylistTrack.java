package com.davidarmbrust.spi.domain.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Provides container for Playlist Track response.
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
