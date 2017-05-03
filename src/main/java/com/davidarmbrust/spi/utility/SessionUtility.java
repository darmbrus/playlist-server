package com.davidarmbrust.spi.utility;

import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides common functionality for session management in controllers
 */
@Component
public class SessionUtility {
    private TokenService tokenService;

    @Autowired
    public SessionUtility(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Session getSession(HttpServletRequest request) {
        Session session = (Session) request.getSession().getAttribute("session");
        return session != null ? tokenService.updateSessionToken(session) : null;
    }
}
