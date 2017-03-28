package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.Token;
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
 * Provides access to Spotify token services for access token management.
 */
@Service
public class TokenService {
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";

    private SpotifyProperties spotifyProperties;
    private RestTemplate restTemplate;

    @Autowired
    public TokenService(
            SpotifyProperties spotifyProperties,
            RestTemplate restTemplate
    ) {
        this.spotifyProperties = spotifyProperties;
        this.restTemplate = restTemplate;
    }

    public Session checkToken(Session session) {
        if(session.getToken() == null) {
            session.setToken(getNewToken(session));
            return session;
        } else if(session.getToken().isValid()) {
            return session;
        } else {
            session.setToken(getRefreshToken(session));
            return session;
        }
    }

    private Token getNewToken(Session session) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        try {
            body.set("grant_type", "authorization_code");
            body.set("code", session.getCode());
            body.set("redirect_uri", spotifyProperties.getCallbackUrl());
            HttpHeaders headers = getTokenHeaders();
            HttpEntity<?> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Token> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, entity, Token.class);
            return response.getBody();
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
        return new Token();
    }

    public Token getRefreshToken(Session session) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        try {
            body.set("grant_type", "refresh_token");
            body.set("refresh_token", session.getToken().getRefreshToken());
            HttpHeaders headers = getTokenHeaders();
            HttpEntity entity = new HttpEntity<>(body, headers);
            ResponseEntity<Token> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, entity, Token.class);
            Token token = response.getBody();
            if (token.getRefreshToken() == null){
                token.setRefreshToken(session.getToken().getRefreshToken());
            }
            return token;
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
        return new Token();
    }

    private HttpHeaders getTokenHeaders() {
        HttpHeaders headers =  new HttpHeaders() {{
            set(AUTHORIZATION, "Basic " + new String(Base64.getEncoder().encode((spotifyProperties.getClientId() + ":" + spotifyProperties.getClientSecret()).getBytes())));
        }};
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
