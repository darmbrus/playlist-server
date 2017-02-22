package com.davidarmbrust.spi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Provides access to Spotify configuration parameters
 */
@Configuration
@ConfigurationProperties(prefix = "spotify")
public class SpotifyProperties {

    private String clientId;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
