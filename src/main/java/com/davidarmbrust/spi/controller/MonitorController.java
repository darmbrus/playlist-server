package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.config.SpotifyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * Provides endpoints for application monitoring
 *
 * @author David Armbrust
 */
@Controller
public class MonitorController {

    @Autowired
    private SpotifyProperties spotifyProperties;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(
            value = "/monitor/properties",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getMonitorRoot() {
        System.out.println(spotifyProperties.getClientId());
        return spotifyProperties.getClientId();
    }

    @RequestMapping(
            value = "/monitor/auth",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getAuthCheck() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/text");
        headers.add("Accept", "*/*");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity responseEntity = restTemplate.exchange("http://localhost:8080/login", HttpMethod.GET, entity, ResponseEntity.class);
        return responseEntity.getBody().toString();
    }
}