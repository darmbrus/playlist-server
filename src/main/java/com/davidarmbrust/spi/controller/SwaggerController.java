package com.davidarmbrust.spi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Provides root endpoints
 *
 * @author David Armbrust
 */
@Controller
public class SwaggerController {

    @RequestMapping(
            value = "/"
    )
    @ResponseBody
    public RedirectView getRoot() {
        return new RedirectView("/swagger-ui.html");
    }
}