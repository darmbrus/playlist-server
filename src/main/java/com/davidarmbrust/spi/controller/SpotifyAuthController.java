package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.config.SpotifyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
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
    private static final String AUTHENTICATION_URL = "https://accounts.spotify.com/authorize";
    private static final String SCOPE = "user-read-private user-read-email";

    @Autowired
    SpotifyAuthController(
            SpotifyProperties spotifyProperties
    ) {
        this.spotifyProperties = spotifyProperties;
    }

    @RequestMapping(
            value = "/login",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getLogin(
            @CookieValue String code
    ) {
        return new ModelAndView("redirect:" + AUTHENTICATION_URL, getOAuthQueryParams());
    }

    @RequestMapping(
            value = "/callback",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getCallback(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String code = request.getParameter("code");
        response.addCookie(new Cookie("code", code));
        return code;
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
