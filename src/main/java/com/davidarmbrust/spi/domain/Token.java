package com.davidarmbrust.spi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Provides storage for tokens received for Spotify
 */
public class Token {
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "token_type")
    private String tokenType;
    @JsonProperty(value = "scope")
    private String scope;
    @JsonProperty(value = "expires_in")
    private int expiresIn;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonIgnore
    private Date created = new Date();

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isValid() {
        long expiresInMillis = expiresIn * 1000;
        return  (created.getTime() + expiresInMillis > new Date().getTime());
    }

    @Override
    public String toString() {
        return "Token{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", scope='" + scope + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", created=" + created +
                '}';
    }
}
