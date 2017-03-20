package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.domain.Album;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Created by Administrator on 3/18/2017.
 */
@Service
public class SpotifyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyService.class);

    private String token;
    private RestTemplate restTemplate;

    @Autowired
    public SpotifyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Album getAlbumById(String albumId) {
        String destination = "https://api.spotify.com/v1/albums/" + albumId;
        Album album = restTemplate.getForObject(destination, Album.class);
        LOGGER.debug("Album name: " + album.getName());
        return album;
    }
}
