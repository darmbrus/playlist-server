package com.davidarmbrust.spi.utility;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Provides access to current date function.
 */
@Component
public class DateUtility {
    public Date getCurrentDate() {
        return new Date();
    }
}
