package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.api.Playlist;
import com.davidarmbrust.spi.domain.api.Track;
import com.davidarmbrust.spi.service.PlaylistService;
import com.davidarmbrust.spi.service.SpotifyService;
import com.davidarmbrust.spi.service.TokenService;
import com.davidarmbrust.spi.utility.SessionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 4/3/2017.
 */
@Controller
@RequestMapping(value = "/playlists")
public class PlaylistsController {
    private static final Logger log = LoggerFactory.getLogger(PlaylistsController.class);

    private TokenService tokenService;
    private SessionUtility sessionUtility;
    private SpotifyService spotifyService;
    private PlaylistService playlistService;

    @Autowired
    private PlaylistsController(
            TokenService tokenService,
            SessionUtility sessionUtility,
            SpotifyService spotifyService,
            PlaylistService playlistService
    ) {
        this.tokenService = tokenService;
        this.sessionUtility = sessionUtility;
        this.spotifyService = spotifyService;
        this.playlistService = playlistService;
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getPlaylists(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        log.trace("Hit GET /playlists/");
        Session session = sessionUtility.getSession(request);
        session = tokenService.updateSessionToken(session);
        List<Playlist> playlists = spotifyService.getUserPlaylists(session);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("playlists/index");
        modelAndView.addObject("listObject", playlists);
        return modelAndView;
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<Track> getPlaylistById(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable String id
    ) {
        log.trace("Hit GET /playlists/" + id);
        Session session = sessionUtility.getSession(request);
        session = tokenService.updateSessionToken(session);
        return spotifyService.getPlaylistTracks(session, id);
    }
}
