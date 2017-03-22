package com.davidarmbrust.spi.domain;

/**
 * Created by Administrator on 3/22/2017.
 */
public class Session {
    private String code;
    private Token token;

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
}
