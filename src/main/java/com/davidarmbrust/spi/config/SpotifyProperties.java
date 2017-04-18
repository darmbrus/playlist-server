package com.davidarmbrust.spi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Provides access to Spotify configuration parameters
 */
@Configuration
@Validated
@ConfigurationProperties(prefix = "spotify")
public class SpotifyProperties {

    @NotNull
    private String clientId;
    @NotNull
    private String clientSecret;
    @NotNull
    private String callbackUrl;
    private String code;
    private String discoverWeeklyId;
    private boolean app;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean useApp() {
        return app;
    }

    public void setApp(boolean app) {
        this.app = app;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscoverWeeklyId() {
        return discoverWeeklyId;
    }

    public void setDiscoverWeeklyId(String discoverWeeklyId) {
        this.discoverWeeklyId = discoverWeeklyId;
    }
}
