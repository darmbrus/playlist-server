package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Provides access to Spotify API
 */
@Service
public class SpotifyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyService.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private SpotifyProperties spotifyProperties;
    private RestTemplate restTemplate;

    private static final String ROOT_URL = "https://api.spotify.com";

    @Autowired
    public SpotifyService(
            RestTemplate restTemplate,
            SpotifyProperties spotifyProperties
    ) {
        this.restTemplate = restTemplate;
        this.spotifyProperties = spotifyProperties;
    }

    public Album getAlbumById(String albumId) {
        String destination = ROOT_URL + "/v1/albums/" + albumId;

        Album album = restTemplate.getForObject(destination, Album.class);
        LOGGER.debug("Album name: " + album.getName());
        return album;
    }

    public User getCurrentUser(Session session) {
        String destination = ROOT_URL + "/v1/me";

        HttpEntity<User> entity = new HttpEntity<>(getAuthHeaders(session));
        ResponseEntity<User> response = restTemplate.exchange(destination, HttpMethod.GET, entity, User.class);
        return response.getBody();
    }

    public void getUserPlaylists(Session session) {
        String destination = ROOT_URL + "/v1/users/"+ session.getUser().getId() + "/playlists";

        HttpEntity entity = new HttpEntity<>(getAuthHeaders(session));
        Paging response = restTemplate.exchange(destination, HttpMethod.GET, entity, Paging.class).getBody();
        ArrayList<Playlist> playlists = response.getItems();
        while (response.getNext() != null) {
            destination = response.getNext();
            response = restTemplate.exchange(destination, HttpMethod.GET, entity, Paging.class).getBody();
            playlists.addAll(response.getItems());
            LOGGER.trace("playlists retrieved");
        }
        LOGGER.debug("Completed playlist request");
    }

    private static Playlist convertPlaylist(LinkedHashMap map) {
        return mapper.convertValue(map, Playlist.class);
    }

    private HttpHeaders getAuthHeaders(Session session) {
        return new HttpHeaders() {{
            set(AUTHORIZATION, "Bearer " + session.getToken().getAccessToken());
        }};
    }
}
