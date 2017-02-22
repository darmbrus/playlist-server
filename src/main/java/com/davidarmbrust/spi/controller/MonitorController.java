package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.config.SpotifyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Provides endpoints for application monitoring
 *
 * @author David Armbrust
 */
@Controller
public class MonitorController {
    @Autowired
    private SpotifyProperties spotifyProperties;

    @RequestMapping(
            value = "/monitor/properties",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getMonitorRoot() {
        System.out.println(spotifyProperties.getClientId());
        return spotifyProperties.getClientId();
    }
}