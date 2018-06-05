package com.vpaveldm.wordgame.dataLayer.model;

public class LoggingModelInDataLayer {
    private String email;
    private String password;

    public LoggingModelInDataLayer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
