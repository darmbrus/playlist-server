package com.davidarmbrust.spi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Provides endpoints for application monitoring
 *
 * @author David Armbrust
 */
@Controller
public class MonitorController {
    @RequestMapping(
            value = "/"
    )
    @ResponseBody
    public String getRoot() {
        return "hello";
    }
}
