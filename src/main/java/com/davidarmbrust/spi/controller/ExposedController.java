package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.Token;
import com.davidarmbrust.spi.domain.api.Playlist;
import com.davidarmbrust.spi.service.AutomationService;
import com.davidarmbrust.spi.service.PlaylistService;
import com.davidarmbrust.spi.service.SpotifyService;
import com.davidarmbrust.spi.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 4/16/2017.
 */
@Controller
@RequestMapping(value = "/exposed")
public class ExposedController {
    private static final Logger log = LoggerFactory.getLogger(ExposedController.class);

    private AutomationService automationService;
    private TokenService tokenService;
    private SpotifyService spotifyService;

    @Autowired
    public ExposedController(
            AutomationService automationService,
            TokenService tokenService,
            SpotifyService spotifyService
    ) {
        this.automationService = automationService;
        this.tokenService = tokenService;
        this.spotifyService = spotifyService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(
            value = "/playlists"

    )
    @ResponseBody
    public List<Playlist> getPlaylists() {
        Session session = getSession();
        return spotifyService.getUserPlaylists(session);
    }

    private Session getSession() {
        Session session = automationService.getSession();
        return tokenService.updateSessionToken(session);
    }
}
