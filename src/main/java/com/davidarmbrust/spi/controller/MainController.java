package com.davidarmbrust.spi.controller;

import com.davidarmbrust.spi.config.AppConfig;
import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.service.AutomationService;
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

/**
 * Provides routes for root and main user display pages.
 */
@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private SessionUtility sessionUtility;
    private AutomationService automationService;
    private SpotifyProperties spotifyProperties;

    @Autowired
    public MainController(
            SessionUtility sessionUtility,
            AutomationService automationService,
            SpotifyProperties spotifyProperties
    ) {
        this.sessionUtility = sessionUtility;
        this.automationService = automationService;
        this.spotifyProperties = spotifyProperties;
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET
    )
    @ResponseBody
    ModelAndView getRoot(
            HttpServletRequest request
    ) {
        if (sessionUtility.getSession(request) == null) {
            return new ModelAndView("redirect:login");
        } else if (spotifyProperties.useApp()) {
            return new ModelAndView("redirect:main");
        } else {
            return new ModelAndView("redirect:main");
        }
    }

    @RequestMapping(
            value = "/main",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response) {
        log.trace("Got to /main");
        String templateName = "main";
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(templateName);
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.addObject("sessionSet", automationService.isSessionSet() ? "true" : "false");
        modelAndView.addObject("sessionCreatedAt", automationService.getSessionSetDate().toString());
        modelAndView.addObject("version", AppConfig.APP_VERSION);
        return modelAndView;
    }

    @RequestMapping(
            value = "/run",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String run() {
        automationService.runSchedule();
        return "complete";
    }
}
