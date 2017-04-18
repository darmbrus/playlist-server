package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.api.Playlist;
import com.davidarmbrust.spi.domain.api.User;
import com.davidarmbrust.spi.service.AutomationService;
import com.davidarmbrust.spi.service.PlaylistService;
import com.davidarmbrust.spi.service.SpotifyService;
import com.davidarmbrust.spi.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 4/16/2017.
 */
@Controller
@RequestMapping(value = "/exposed")
public class ExposedController {
    private static final Logger log = LoggerFactory.getLogger(ExposedController.class);
    private static final String REACT_APP = "http://localhost:3000";

    private AutomationService automationService;
    private TokenService tokenService;
    private SpotifyService spotifyService;
    private PlaylistService playlistService;

    @Autowired
    public ExposedController(
            AutomationService automationService,
            TokenService tokenService,
            SpotifyService spotifyService,
            PlaylistService playlistService
    ) {
        this.automationService = automationService;
        this.tokenService = tokenService;
        this.spotifyService = spotifyService;
        this.playlistService = playlistService;
    }

    @CrossOrigin(origins = REACT_APP)
    @RequestMapping(
            value = "/user",
            method = RequestMethod.GET
    )
    @ResponseBody
    public User getUser() {
        log.info("Hit /exposed/user");
        Session session = getSession();
        return session.getUser();
    }

    @CrossOrigin(origins = REACT_APP)
    @RequestMapping(
            value = "/playlists",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<Playlist> getPlaylists() {
        log.info("Hit /exposed/playlists");
        Session session = getSession();
        return spotifyService.getUserPlaylists(session);
    }

    @CrossOrigin(origins = REACT_APP)
    @RequestMapping(
            value = "/playlists/{id}/random",
            method = RequestMethod.POST
    )
    @ResponseBody
    public void createRandomPlaylist(@PathVariable String id) {
        Session session = getSession();
//        playlistService.createRandomPlaylist(session, id);
        log.info("Hit /exposed/playlists/" + id + "/random");
    }

    private Session getSession() {
        Session session = automationService.getSession();
        return tokenService.updateSessionToken(session);
    }
}
