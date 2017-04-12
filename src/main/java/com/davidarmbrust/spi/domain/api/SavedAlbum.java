package com.davidarmbrust.spi.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Provides container for Saved Album response.
 */
public class SavedAlbum {
    @JsonProperty(value = "added_at")
    private String addedAt;
    private Album album;

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
