package com.vpaveldm.wordgame.presentationLayer.model;

public class LoggingModelInPresentationLayer {

    private boolean isSuccess;
    private String email;
    private String password;

    public LoggingModelInPresentationLayer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
