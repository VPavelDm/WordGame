package com.vpaveldm.wordgame.dataLayer.model;

import android.content.Intent;

public class LoggingModelInDataLayer {
    private String email;
    private String password;
    private Intent data;

    private LoggingModelInDataLayer(String email, String password, Intent data) {
        this.email = email;
        this.password = password;
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Intent getData() {
        return data;
    }

    public static class Builder {
        private String email;
        private String password;
        private Intent data;

        public LoggingModelInDataLayer.Builder addEmail(String email) {
            this.email = email;
            return this;
        }

        public LoggingModelInDataLayer.Builder addPassword(String password) {
            this.password = password;
            return this;
        }

        public LoggingModelInDataLayer.Builder addData(Intent data) {
            this.data = data;
            return this;
        }

        public LoggingModelInDataLayer create() {
            return new LoggingModelInDataLayer(email, password, data);
        }
    }
}
