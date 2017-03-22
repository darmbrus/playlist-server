package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.domain.Album;
import com.davidarmbrust.spi.domain.Token;
import com.davidarmbrust.spi.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;


/**
 * Provides access to Spotify API
 */
@Service
public class SpotifyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyService.class);

    @Autowired
    private SpotifyProperties spotifyProperties;

    private String code;
    private RestTemplate restTemplate;

    @Autowired
    public SpotifyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Album getAlbumById(String albumId) {
        String destination = "https://api.spotify.com/v1/albums/" + albumId;
        Album album = restTemplate.getForObject(destination, Album.class);
        LOGGER.debug("Album name: " + album.getName());
        return album;
    }

    public User getCurrentUser() {
        HttpEntity<User> entity = new HttpEntity<>(getAuthHeaders());
        String destination = "https://api.spotify.com/v1/me";
        ResponseEntity<User> response = restTemplate.exchange(destination, HttpMethod.GET, entity, User.class);
        return response.getBody();
    }

    private Token getNewToken() {
        String destination = "https://accounts.spotify.com/api/token";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        try {
            body.set("grant_type", "authorization_code");
            body.set("code", code);
            body.set("redirect_uri", spotifyProperties.getCallbackUrl());
            HttpHeaders headers = getTokenHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<?> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Token> response = restTemplate.exchange(destination, HttpMethod.POST, entity, Token.class);
            return response.getBody();
        } catch (RestClientException e) {
            LOGGER.debug(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.debug("Http client request failed: " + e.getMessage(), e);
        }
        return new Token();
    }

    private HttpHeaders getTokenHeaders() {
        return new HttpHeaders() {{
            set(AUTHORIZATION, "Basic " + new String(Base64.getEncoder().encode((spotifyProperties.getClientId() + ":" + spotifyProperties.getClientSecret()).getBytes())));
        }};
    }

    private HttpHeaders getAuthHeaders() {
        return new HttpHeaders() {{
            set(AUTHORIZATION, "Bearer " + getNewToken().getAccessToken());
        }};
    }
}
