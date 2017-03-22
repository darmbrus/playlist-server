package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.domain.Album;
import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.Token;
import com.davidarmbrust.spi.domain.User;
import com.davidarmbrust.spi.service.SpotifyService;
import com.davidarmbrust.spi.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides access to Spotify domain objects
 */
@Controller
public class ObjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectController.class);

    private SpotifyService spotifyService;
    private TokenService tokenService;

    @Autowired
    public ObjectController(
            SpotifyService spotifyService,
            TokenService tokenService
    ) {
        this.spotifyService = spotifyService;
        this.tokenService = tokenService;
    }

    @RequestMapping(
            value = "/getAlbum/{id}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getAlbum(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.trace("Hit /getAlbum/" + id);
        Album album = spotifyService.getAlbumById(id);
        return album.getName();
    }

    @RequestMapping(
            value = "/getCurrentUser/",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.trace("Hit /getCurrentUser/");
        Session session = getSession(request);
        session = tokenService.checkToken(session);
        User user = spotifyService.getCurrentUser(session);
        return user.getEmail();
    }

    private Session getSession(HttpServletRequest request) {
        return (Session) request.getSession().getAttribute("session");
    }

    private String getCode(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("code");
    }
}
