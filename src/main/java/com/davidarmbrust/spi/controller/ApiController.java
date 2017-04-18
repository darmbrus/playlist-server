package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.config.AppConfig;
import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.api.Playlist;
import com.davidarmbrust.spi.domain.api.User;
import com.davidarmbrust.spi.service.AutomationService;
import com.davidarmbrust.spi.service.PlaylistService;
import com.davidarmbrust.spi.service.SpotifyService;
import com.davidarmbrust.spi.utility.SessionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Provides endpoints for API data access.
 */
@Controller
@RequestMapping(value = "/api")
public class ApiController {
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    private SpotifyService spotifyService;
    private PlaylistService playlistService;
    private SessionUtility sessionUtility;
    private AutomationService automationService;

    @Autowired
    public ApiController(
            SpotifyService spotifyService,
            PlaylistService playlistService,
            SessionUtility sessionUtility,
            AutomationService automationService
    ) {
        this.spotifyService = spotifyService;
        this.playlistService = playlistService;
        this.sessionUtility = sessionUtility;
        this.automationService = automationService;
    }

    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @ResponseBody
    public HashMap<String, String> getInfo() {
        log.info("Hit /api/info");
        HashMap<String, String > info = new HashMap<>();
        info.put("version", AppConfig.APP_VERSION);
        info.put("cached_user", automationService.getSession().getUser().getId());
        return info;
    }

    @RequestMapping(
            value = "/user",
            method = RequestMethod.GET
    )
    @ResponseBody
    public User getUser(HttpServletRequest request) {
        log.info("Hit /api/user");
        Session session = sessionUtility.getSession(request);
        return session.getUser();
    }

    @RequestMapping(
            value = "/playlists",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<Playlist> getPlaylists(HttpServletRequest request) {
        log.info("Hit /api/playlists");
        Session session = sessionUtility.getSession(request);
        return spotifyService.getUserPlaylists(session);
    }

    @RequestMapping(
            value = "/playlists/{id}/random",
            method = RequestMethod.POST
    )
    @ResponseBody
    public void createRandomPlaylist(@PathVariable String id, HttpServletRequest request) {
        log.info("Hit /api/playlists/" + id + "/random");
        Session session = sessionUtility.getSession(request);
        playlistService.createRandomPlaylist(session, id);
    }
}
