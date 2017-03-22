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
 * Created by Administrator on 3/21/2017.
 */
@Service
public class TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

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
        if(session.getToken().isValid()) {
            return session;
        } else {
            session.setToken(getNewToken(session));
            return session;
        }
    }

    private Token getNewToken(Session session) {
        String destination = "https://accounts.spotify.com/api/token";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        try {
            body.set("grant_type", "authorization_code");
            body.set("code", session.getCode());
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
}
