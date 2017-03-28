package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.domain.*;
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
import java.util.List;

/**
 * Provides access to Spotify domain objects
 */
@Controller
public class ObjectController {

    private static final Logger log = LoggerFactory.getLogger(ObjectController.class);

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
        log.trace("Hit /getAlbum" + id);
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
        log.trace("Hit /getCurrentUser");
        Session session = sessionUtility.getSession(request);
        session = tokenService.checkToken(session);
        User user = spotifyService.getCurrentUser(session);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showObject");
        modelAndView.addObject("object", user);
        return modelAndView;
    }

    @RequestMapping(
            value = "/getPlaylists",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getUserPlaylists(HttpServletRequest request, HttpServletResponse response) {
        log.trace("Hit /getPlaylists");
        Session session = sessionUtility.getSession(request);
        List<Playlist> playlists = spotifyService.getUserPlaylists(session);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listPlaylists");
        modelAndView.addObject("listObject", playlists);
        return modelAndView;
    }

    @RequestMapping(
            value = "/getPlaylist/{id}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getPlaylistTracks(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        log.trace("Hit /getPlaylist/" + id);
        Session session = sessionUtility.getSession(request);
        List<Track> tracks = spotifyService.getPlaylistTracks(session, id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listObjects");
        modelAndView.addObject("listObject", tracks);
        return modelAndView;
    }

    @RequestMapping(
            value = "/getRefreshToken",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getToken(HttpServletRequest request, HttpServletResponse response) {
        log.trace("Hit /getRefreshToken");
        Session session = sessionUtility.getSession(request);
        Token token = tokenService.getRefreshToken(session);
        session.setToken(token);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showObject");
        modelAndView.addObject("object", token);
        return modelAndView;
    }
}
