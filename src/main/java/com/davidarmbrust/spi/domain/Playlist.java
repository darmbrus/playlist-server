package com.davidarmbrust.spi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Provides a container for Playlist objects from Spotify.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Playlist {
    private boolean collaborative;
    private String description;
    private String href;
    private String id;
    private String name;

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

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "collaborative=" + collaborative +
                ", description='" + description + '\'' +
                ", href='" + href + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
