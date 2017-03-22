package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.utility.SessionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides OAuthentication for Spotify
 *
 * @author David Armbrust
 */
@Controller
public class SpotifyAuthController {

    private SpotifyProperties spotifyProperties;
    private SessionUtility sessionUtility;
    private static final String AUTHENTICATION_URL = "https://accounts.spotify.com/authorize";
    private static final String SCOPE = "user-read-private";
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyAuthController.class);

    @Autowired
    SpotifyAuthController(
            SpotifyProperties spotifyProperties,
            SessionUtility sessionUtility
    ) {
        this.spotifyProperties = spotifyProperties;
        this.sessionUtility = sessionUtility;
    }

    @RequestMapping(
            value = "/login",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getLogin(HttpServletRequest request, HttpServletResponse re) {
        LOGGER.trace("Reached Login");
        Session session = sessionUtility.getSession(request);
        if (session != null) {
            return new ModelAndView("redirect:main");
        } else {
            return new ModelAndView("redirect:" + AUTHENTICATION_URL, getOAuthQueryParams());
        }
    }

    @RequestMapping(
            value = "/callback",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getCallback( HttpServletRequest request, HttpServletResponse response ) {
        String code = request.getParameter("code");
        LOGGER.debug("Got to callback: codeSize = " + code.length());
        Session session = new Session(code);
        request.getSession().setAttribute("session", session);

        return new ModelAndView("redirect:main");
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
