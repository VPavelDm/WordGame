package com.vpaveldm.wordgame.domainLayer.model;

public class LoggingModelInDomainLayer {
    private String email;
    private String password;

    public LoggingModelInDomainLayer(String email, String password) {
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
