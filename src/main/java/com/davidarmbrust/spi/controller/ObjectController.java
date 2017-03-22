package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.domain.Album;
import com.davidarmbrust.spi.domain.User;
import com.davidarmbrust.spi.service.SpotifyService;
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

    @Autowired
    private SpotifyService spotifyService;

    @RequestMapping(
            value = "/getAlbum/{id}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getAlbum(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.trace("Hit /getAlbum/" + id);
        spotifyService.setCode(getCode(request));
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
        spotifyService.setCode(getCode(request));
        User user = spotifyService.getCurrentUser();
        return user.getEmail();
    }

    private String getCode(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("code");
    }
}
