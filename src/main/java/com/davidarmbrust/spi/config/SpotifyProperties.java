package com.davidarmbrust.spi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Provides access to Spotify configuration parameters
 */
@Configuration
@Validated
@ConfigurationProperties
public class SpotifyProperties {

    @NotNull
    @Value("${spotify.clientId}")
    private String clientId;
    @NotNull
    @Value("${spotify.clientSecret}")
    private String clientSecret;
    @NotNull
    @Value("${spotify.callbackUrl}")
    private String callbackUrl;
    @Value("${test.code}")
    private String code;
    @Value("${spotify.discoverWeeklyId}")
    private String discoverWeeklyId;
    @Value("${spotify.releaseRadarId}")
    private String releaseRadarId;
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

    public String getReleaseRadarId() {
        return releaseRadarId;
    }

    public void setReleaseRadarId(String releaseRadarId) {
        this.releaseRadarId = releaseRadarId;
    }
}
