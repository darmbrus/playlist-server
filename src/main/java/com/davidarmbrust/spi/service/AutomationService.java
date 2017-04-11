package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.api.Album;
import com.davidarmbrust.spi.domain.api.Playlist;
import com.davidarmbrust.spi.domain.api.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Provides automated methods for logged in user.
 */
@Service
public class AutomationService {
    private static final Logger log = LoggerFactory.getLogger(AutomationService.class);

    private boolean sessionSet = false;
    private Session session;

    private TokenService tokenService;
    private PlaylistService playlistService;

    @Autowired
    public AutomationService(
            TokenService tokenService,
            PlaylistService playlistService
    ) {
        this.tokenService = tokenService;
        this.playlistService = playlistService;
    }

    @Scheduled(cron = "0 0 2 ? * MON")
    public void runSchedule() {
        if (sessionSet) {
            log.debug("Session set: " + session.toString());
            session = tokenService.updateSessionToken(session);
            playlistService.createRandomDiscoverWeekly(session);
        } else {
            log.error("Session not set");
        }
    }

    public void setSession(Session session) {
        this.sessionSet = true;
        this.session = session;
    }
}
