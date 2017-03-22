package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.domain.Album;
import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.User;
import com.davidarmbrust.spi.service.SpotifyService;
import com.davidarmbrust.spi.service.TokenService;
import com.davidarmbrust.spi.utility.SessionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private SessionUtility sessionUtility;

    @Autowired
    public ObjectController(
            SpotifyService spotifyService,
            TokenService tokenService,
            SessionUtility sessionUtility
    ) {
        this.spotifyService = spotifyService;
        this.tokenService = tokenService;
        this.sessionUtility = sessionUtility;
    }

    @RequestMapping(
            value = "/getAlbum/{id}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getAlbum(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.trace("Hit /getAlbum" + id);
        Album album = spotifyService.getAlbumById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showObject");
        modelAndView.addObject("object", album);
        return modelAndView;
    }

    @RequestMapping(
            value = "/getCurrentUser",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.trace("Hit /getCurrentUser");
        Session session = sessionUtility.getSession(request);
        session = tokenService.checkToken(session);
        User user = spotifyService.getCurrentUser(session);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showObject");
        modelAndView.addObject("object", user);
        return modelAndView;
    }

    private Session getSession(HttpServletRequest request) {
        return (Session) request.getSession().getAttribute("session");
    }

    private String getCode(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("code");
    }
}
