package com.vpaveldm.wordgame.dataLayer.model;

import android.content.Intent;

public class LoggingModel {
    private final String email;
    private final String password;
    private final Intent data;

    private LoggingModel(String email, String password, Intent data) {
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

        public LoggingModel.Builder addEmail(String email) {
            this.email = email;
            return this;
        }

        public LoggingModel.Builder addPassword(String password) {
            this.password = password;
            return this;
        }

        public LoggingModel.Builder addData(Intent data) {
            this.data = data;
            return this;
        }

        public LoggingModel create() {
            return new LoggingModel(email, password, data);
        }
    }
}
