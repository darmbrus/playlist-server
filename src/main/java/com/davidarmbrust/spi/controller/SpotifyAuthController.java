package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.service.AutomationService;
import com.davidarmbrust.spi.service.SpotifyService;
import com.davidarmbrust.spi.service.TokenService;
import com.davidarmbrust.spi.utility.SessionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides OAuthentication for Spotify
 */
@Controller
public class SpotifyAuthController {

    private SpotifyProperties spotifyProperties;
    private SessionUtility sessionUtility;
    private SpotifyService spotifyService;
    private TokenService tokenService;
    private AutomationService automationService;
    private static final String AUTHENTICATION_URL = "https://accounts.spotify.com/authorize";
    private static final String SCOPE = "user-read-private playlist-modify-private user-library-read";
    private static final Logger log = LoggerFactory.getLogger(SpotifyAuthController.class);

    @Autowired
    SpotifyAuthController(
            SpotifyProperties spotifyProperties,
            SessionUtility sessionUtility,
            SpotifyService spotifyService,
            TokenService tokenService,
            AutomationService automationService
    ) {
        this.spotifyProperties = spotifyProperties;
        this.sessionUtility = sessionUtility;
        this.spotifyService = spotifyService;
        this.tokenService = tokenService;
        this.automationService = automationService;
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getRoot(HttpServletRequest request) {
        Session session = sessionUtility.getSession(request);
        if (session != null) {
            return new ModelAndView("index.html");
        } else {
            return new ModelAndView("redirect:login");
        }
    }

    @RequestMapping(
            value = "/login",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getLogin() {
        log.trace("Reached Login");
        return new ModelAndView("redirect:" + AUTHENTICATION_URL, getOAuthQueryParams());
    }

    @RequestMapping(
            value = "/callback",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getCallback(HttpServletRequest request) {
        String code = request.getParameter("code");
        log.debug("Got to callback: codeSize = " + code.length());
        Session session = new Session(code);
        session = tokenService.updateSessionToken(session);
        session.setUser(spotifyService.getCurrentUser(session));
        request.getSession().setAttribute("session", session);
        automationService.setSession(session);

        return new ModelAndView("redirect:/");
    }

    private Map<String, String> getOAuthQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("response_type", "code");
        queryParams.put("scope", SCOPE);
        queryParams.put("client_id", spotifyProperties.getClientId());
        queryParams.put("redirect_uri", spotifyProperties.getCallbackUrl());
        return queryParams;
    }
}
