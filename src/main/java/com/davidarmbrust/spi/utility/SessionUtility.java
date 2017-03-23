package com.davidarmbrust.spi.utility;

import com.davidarmbrust.spi.domain.Session;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides common functionality for session management in controllers
 */
@Component
public class SessionUtility {
    public Session getSession(HttpServletRequest request) {
        return (Session) request.getSession().getAttribute("session");
    }
}
