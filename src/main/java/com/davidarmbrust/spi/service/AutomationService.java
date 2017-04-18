package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Provides automated methods for logged in user.
 */
@Service
public class AutomationService {
    private static final Logger log = LoggerFactory.getLogger(AutomationService.class);

    private boolean sessionSet = false;
    private Session session;

    private Date sessionSetDate;

    private DateUtility dateUtility;
    private TokenService tokenService;

    private PlaylistService playlistService;

    @Autowired
    public AutomationService(
            TokenService tokenService,
            PlaylistService playlistService,
            DateUtility dateUtility
    ) {
        this.tokenService = tokenService;
        this.playlistService = playlistService;
        this.dateUtility = dateUtility;
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

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        if (!this.sessionSet) {
            this.sessionSet = true;
            this.sessionSetDate = dateUtility.getCurrentDate();
            this.session = session;
        }
    }

    public Date getSessionSetDate() {
        return sessionSetDate;
    }

    public boolean isSessionSet() {
        return sessionSet;
    }
}
