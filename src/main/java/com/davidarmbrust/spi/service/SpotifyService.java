package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Provides access to Spotify API
 */
@Service
@SuppressWarnings("unchecked")
public class SpotifyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyService.class);
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
        LOGGER.debug("Album name: " + album.getName());
        return album;
    }

    /**
     * Retrieves the current user information from Spotify.
     */
    public User getCurrentUser(Session session) {
        String destination = ROOT_URL + "/v1/me";
        HttpEntity<User> entity = new HttpEntity<>(getAuthHeaders(session));
        ResponseEntity<User> response = restTemplate.exchange(destination, HttpMethod.GET, entity, User.class);
        return response.getBody();
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
