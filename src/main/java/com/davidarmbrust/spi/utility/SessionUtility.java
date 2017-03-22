package com.davidarmbrust.spi.utility;

import com.davidarmbrust.spi.domain.Session;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 3/22/2017.
 */
@Component
public class SessionUtility {
    public Session getSession(HttpServletRequest request) {
        return (Session) request.getSession().getAttribute("session");
    }
}
