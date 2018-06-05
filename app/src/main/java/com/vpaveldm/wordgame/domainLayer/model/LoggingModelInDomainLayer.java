package com.vpaveldm.wordgame.domainLayer.model;

public class LoggingModelInDomainLayer {
    private String email;
    private String password;
    private Error mError;
    private boolean isSuccess = true;

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

    public Error getError() {
        return mError;
    }

    public void setError(Error error) {
        isSuccess = false;
        mError = error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public static class Error {
        private String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
