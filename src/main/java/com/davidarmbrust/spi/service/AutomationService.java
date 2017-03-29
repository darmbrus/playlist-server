package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.api.Playlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 3/29/2017.
 */
@Service
public class AutomationService {
    private static final Logger log = LoggerFactory.getLogger(AutomationService.class);

    private boolean sessionSet = false;
    private Session session;

    private TokenService tokenService;
    private SpotifyService spotifyService;

    @Autowired
    public AutomationService(
            TokenService tokenService,
            SpotifyService spotifyService
    ) {
        this.tokenService = tokenService;
        this.spotifyService = spotifyService;
    }

    @Scheduled(cron = "0 0 2 ? * MON")
    public void runSchedule() {
        if (sessionSet) {
            log.debug("Session set: " + session.toString());
            session = tokenService.updateSessionToken(session);
            List<Playlist> playlists = spotifyService.getUserPlaylists(session);
            log.debug("Playlists found: " + playlists.size() + " " + playlists.get(0).getName());
        } else {
            log.debug("Session not set");
        }
    }

    public void setSession(Session session) {
        this.sessionSet = true;
        this.session = session;
    }
}
