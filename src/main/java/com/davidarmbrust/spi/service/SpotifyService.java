package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.api.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides access to Spotify API objects.
 */
@Service
@SuppressWarnings("unchecked")
public class SpotifyService {
    private static final Logger log = LoggerFactory.getLogger(SpotifyService.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private RestTemplate restTemplate;

    private static final String ROOT_URL = "https://api.spotify.com";
    private static final String API_VERSION = "/v1";

    @Autowired
    public SpotifyService(
            RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
    }

    public Album getAlbumById(Session session, String albumId) {
        String destination = ROOT_URL + API_VERSION + "/albums/" + albumId;
        HttpEntity<Album> entity = new HttpEntity<>(getAuthHeaders(session));
        Album album = restTemplate.exchange(destination, HttpMethod.GET, entity, Album.class).getBody();
        album.setTracksList(resolvePaging(album.getPagingTracks(), Track.class, session));
        log.debug("Album found: " + album.getName());
        return album;
    }

    /**
     * Retrieves the current user information from Spotify.
     */
    public User getCurrentUser(Session session) {
        String destination = ROOT_URL + API_VERSION + "/me";
        HttpEntity<User> entity = new HttpEntity<>(getAuthHeaders(session));
        ResponseEntity<User> response = restTemplate.exchange(destination, HttpMethod.GET, entity, User.class);
        log.debug("User found: " + response.getBody());
        return response.getBody();
    }

    public List<Album> getUserSavedAlbums(Session session) {
        String destination = ROOT_URL + API_VERSION + "/me/albums";
        HttpEntity entity = new HttpEntity(getAuthHeaders(session));
        Paging response = restTemplate.exchange(destination, HttpMethod.GET, entity, Paging.class).getBody();
        List<SavedAlbum> savedAlbums = resolvePaging(response, SavedAlbum.class, session);
        return savedAlbums.stream()
                .map(SavedAlbum::getAlbum)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of tracks for a given playlist for the current user.
     */
    public List<Track> getPlaylistTracks(Session session, String playlistId) {
        return getPlaylistTracks(session, session.getUser().getId(), playlistId);
    }

    /**
     * Retrieves a list of tracks for a given playlist.
     */
    List<Track> getPlaylistTracks(Session session, String userId, String playlistId) {
        String destination = ROOT_URL + API_VERSION + "/users/" + userId + "/playlists/" + playlistId + "/tracks";
        HttpEntity entity = new HttpEntity(getAuthHeaders(session));
        Paging response = restTemplate.exchange(destination, HttpMethod.GET, entity, Paging.class).getBody();
        List<PlaylistTrack> playlistTracks = resolvePaging(response, PlaylistTrack.class, session);
        log.debug("Playlist tracks found: " + playlistTracks.size());
        return playlistTracks.stream()
                .map(PlaylistTrack::getTrack)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the current users playlists from Spotify.
     */
    public List<Playlist> getUserPlaylists(Session session) {
        String destination = ROOT_URL + API_VERSION + "/users/" + session.getUser().getId() + "/playlists";
        HttpEntity entity = new HttpEntity<>(getAuthHeaders(session));
        Paging response = restTemplate.exchange(destination, HttpMethod.GET, entity, Paging.class).getBody();
        return resolvePaging(response, Playlist.class, session);
    }

    public Playlist getUserPlaylist(Session session, String id) {
        return getUserPlaylist(session, session.getUser().getId(), id);
    }

    /**
     * Retrieves a playlist by ID for a user from Spotify.
     */
    public Playlist getUserPlaylist(Session session, String username, String id) {
        String destination = ROOT_URL + API_VERSION + "/users/" + username + "/playlists/" + id;
        HttpEntity entity = new HttpEntity(getAuthHeaders(session));
        return restTemplate.exchange(destination, HttpMethod.GET, entity, Playlist.class).getBody();
    }

    /**
     * Posts new playlist to a users account.
     */
    public Playlist createUserPlaylist(String name, Session session) {
        String destination = ROOT_URL + API_VERSION + "/users/" + session.getUser().getId() + "/playlists";
        HttpHeaders headers = getAuthHeaders(session);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("public", "false");
        body.put("collaborative", "false");
        HttpEntity<Playlist> entity = new HttpEntity(body, headers);
        Playlist playlist = restTemplate.exchange(destination, HttpMethod.POST, entity, Playlist.class).getBody();
        log.info("Playlist created: " + playlist.getName());
        return playlist;
    }

    /**
     * Posts all tracks in an album to a playlist.
     */
    public void addAlbumToPlaylist(Album album, Playlist playlist, Session session) {
        String destination = ROOT_URL + API_VERSION + "/users/" + session.getUser().getId() + "/playlists/" + playlist.getId() + "/tracks";
        if (album.getTracksList() == null) {
            album = getAlbumById(session, album.getId());
        }
        List<String> trackList = album.buildTrackUriList();
        HashMap<String, List<String>> body = new HashMap<>();
        body.put("uris", trackList);
        HttpHeaders headers = getAuthHeaders(session);
        HttpEntity entity = new HttpEntity(body, headers);
        try {
            ResponseEntity response = restTemplate.exchange(destination, HttpMethod.POST, entity, Object.class);
            log.debug("Tracks added to playlist: " + playlist + " " + response.getBody());
        } catch (HttpClientErrorException ex) {
            log.debug(ex.getMessage());
            int sleep = Integer.parseInt(ex.getResponseHeaders().getFirst("Retry-After"));
            try {
                log.debug("Thread sleeping for:" + sleep + " seconds");
                Thread.sleep(sleep * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
    @Deprecated
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
