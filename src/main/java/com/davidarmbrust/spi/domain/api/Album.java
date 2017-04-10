package com.davidarmbrust.spi.domain.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides container for Album information.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {

    private String id;
    private String name;
    @JsonProperty("tracks")
    private Paging pagingTracks;
    private List<Track> tracksList;

    private Artist[] artists;

    public Artist[] getArtists() {
        return artists;
    }

    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

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

    public Paging getPagingTracks() {
        return pagingTracks;
    }

    public void setPagingTracks(Paging pagingTracks) {
        this.pagingTracks = pagingTracks;
    }

    public List<Track> getTracksList() {
        return tracksList;
    }

    public void setTracksList(List<Track> tracksList) {
        this.tracksList = tracksList;
    }

    public List<String> buildTrackUriList() {
        return tracksList.stream()
                .map(Track::getUri)
                .collect(Collectors.toList());
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
