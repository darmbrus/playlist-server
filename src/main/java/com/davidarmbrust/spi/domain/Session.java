package com.davidarmbrust.spi.domain;

import java.util.Date;

/**
 * Created by Administrator on 3/22/2017.
 */
public class Session {
    private String code;
    private Token token;
    private User user;
    private Date createdAt = new Date();

    public Session(String code) {
        this.code = code;
        this.token = new Token();
    }

    public Session(String code, Token token) {
        this.code = code;
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Session{" +
                "code='" + code + '\'' +
                ", token=" + token +
                ", user=" + user +
                ", createdAt=" + createdAt +
                '}';
    }
}
