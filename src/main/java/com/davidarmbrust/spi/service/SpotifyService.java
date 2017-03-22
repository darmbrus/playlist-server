package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.domain.Album;
import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;


/**
 * Provides access to Spotify API
 */
@Service
public class SpotifyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyService.class);

    private SpotifyProperties spotifyProperties;
    private RestTemplate restTemplate;

    @Autowired
    public SpotifyService(
            RestTemplate restTemplate,
            SpotifyProperties spotifyProperties
    ) {
        this.restTemplate = restTemplate;
        this.spotifyProperties = spotifyProperties;
    }

    public Album getAlbumById(String albumId) {
        String destination = "https://api.spotify.com/v1/albums/" + albumId;
        Album album = restTemplate.getForObject(destination, Album.class);
        LOGGER.debug("Album name: " + album.getName());
        return album;
    }

    public User getCurrentUser(Session session) {
        HttpEntity<User> entity = new HttpEntity<>(getAuthHeaders(session));
        String destination = "https://api.spotify.com/v1/me";
        ResponseEntity<User> response = restTemplate.exchange(destination, HttpMethod.GET, entity, User.class);
        return response.getBody();
    }

    private HttpHeaders getAuthHeaders(Session session) {
        return new HttpHeaders() {{
            set(AUTHORIZATION, "Bearer " + session.getToken().getAccessToken());
        }};
    }
}
