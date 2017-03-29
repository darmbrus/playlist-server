package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.domain.*;
import com.davidarmbrust.spi.domain.api.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides access to Spotify API
 */
@Service
@SuppressWarnings("unchecked")
public class SpotifyService {
    private static final Logger log = LoggerFactory.getLogger(SpotifyService.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private RestTemplate restTemplate;

    private static final String ROOT_URL = "https://api.spotify.com";

    @Autowired
    public SpotifyService(
            RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
    }

    public Album getAlbumById(String albumId) {
        String destination = ROOT_URL + "/v1/albums/" + albumId;

        Album album = restTemplate.getForObject(destination, Album.class);
        album.setTracksList(resolvePaging(album.getPagingTracks(), Track.class));
        log.debug("Album found: " + album.getName());
        return album;
    }

    /**
     * Retrieves the current user information from Spotify.
     */
    public User getCurrentUser(Session session) {
        String destination = ROOT_URL + "/v1/me";
        HttpEntity<User> entity = new HttpEntity<>(getAuthHeaders(session));
        ResponseEntity<User> response = restTemplate.exchange(destination, HttpMethod.GET, entity, User.class);
        log.debug("User found: " + response.getBody());
        return response.getBody();
    }

    /**
     * Retrieves a list of tracks for a given playlist for the current user.
     */
    public List<Track> getPlaylistTracks(Session session, String playlistId) {
        return getPlaylistTracks(session, session.getUser().getId(), playlistId);
    }

    /**
     * Retrieves a list of tracks for spotify's given playlist. Only applicable for discover
     * weekly retrieval.
     */
    public List<Track> getDiscoverWeeklyTracks(Session session, String playlistId) {
        return getPlaylistTracks(session, "spotify", playlistId);
    }

    /**
     * Retrieves a list of tracks for a given playlist.
     */
    private List<Track> getPlaylistTracks(Session session, String userId, String playlistId) {
        String destination = ROOT_URL + "/v1/users/" + userId + "/playlists/" + playlistId + "/tracks";
        HttpEntity entity = new HttpEntity(getAuthHeaders(session));
        Paging<LinkedHashMap> response = restTemplate.exchange(destination, HttpMethod.GET, entity, Paging.class).getBody();
        List<PlaylistTrack> playlistTracks =  resolvePaging(response, PlaylistTrack.class, session);
        log.debug("Playlist tracks found: " + playlistTracks.size());
        return playlistTracks.stream()
                .map(PlaylistTrack::getTrack)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the current users playlists from Spotify.
     */
    public List<Playlist> getUserPlaylists(Session session) {
        String destination = ROOT_URL + "/v1/users/" + session.getUser().getId() + "/playlists";
        HttpEntity entity = new HttpEntity<>(getAuthHeaders(session));
        Paging<LinkedHashMap> response = restTemplate.exchange(destination, HttpMethod.GET, entity, Paging.class).getBody();
        return resolvePaging(response, Playlist.class, session);
    }

    /**
     * Posts new playlist to a users account.
     */
    public Playlist createUserPlaylist(String name, Session session) {
        String destination = ROOT_URL + "/v1/users/" + session.getUser().getId() + "/playlists";
        HttpHeaders headers = getAuthHeaders(session);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("public", "false");
        body.put("collaborative", "false");
        HttpEntity<Playlist> entity = new HttpEntity(body, headers);
        Playlist playlist = restTemplate.exchange(destination, HttpMethod.POST, entity, Playlist.class).getBody();
        log.debug("Playlist created: " + playlist.getName());
        return playlist;
    }

    /**
     * Resolves a paging response from Spotify to a list of objects.
     */
    private List resolvePaging(Paging response, Class convertTo, Session session) {
        List output = response.getConvertedItems(mapper, convertTo);
        HttpEntity entity = new HttpEntity<>(getAuthHeaders(session));
        String destination;
        while (response.getNext() != null) {
            destination = response.getNext();
            response = restTemplate.exchange(destination, HttpMethod.GET, entity, Paging.class).getBody();
            output.addAll(response.getConvertedItems(mapper, convertTo));
        }
        return output;
    }

    /**
     * Resolves a paging response from Spotify to a list of objects.
     */
    private List resolvePaging(Paging response, Class convertTo) {
        List output = response.getConvertedItems(mapper, convertTo);
        String destination;
        while (response.getNext() != null) {
            destination = response.getNext();
            response = restTemplate.getForObject(destination, Paging.class);
            output.addAll(response.getConvertedItems(mapper, convertTo));
        }
        return output;
    }

    private HttpHeaders getAuthHeaders(Session session) {
        return new HttpHeaders() {{
            set(AUTHORIZATION, "Bearer " + session.getToken().getAccessToken());
        }};
    }
}
