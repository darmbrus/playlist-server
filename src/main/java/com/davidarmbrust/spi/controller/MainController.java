package com.davidarmbrust.spi.controller;

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
 * Created by Administrator on 3/22/2017.
 */
@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private SessionUtility sessionUtility;

    @Autowired
    public MainController(SessionUtility sessionUtility) {
        this.sessionUtility = sessionUtility;
    }

    @RequestMapping(
            value = "/main",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.trace("Got to /main");
        String templateName = "main";
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(templateName);
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.addObject("session", sessionUtility.getSession(request));
        modelAndView.addObject("sessionCreatedAt", sessionUtility.getSession(request).getCreatedAt().toString());
        return modelAndView;
    }
}