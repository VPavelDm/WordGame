package com.vpaveldm.wordgame.presentationLayer.model;

import android.content.Intent;

public class LoggingModelInPresentationLayer {

    private String email;
    private String password;
    private Intent data;

    private LoggingModelInPresentationLayer(String email, String password, Intent data) {
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

        public Builder addEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder addPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder addData(Intent data) {
            this.data = data;
            return this;
        }

        public LoggingModelInPresentationLayer create() {
            return new LoggingModelInPresentationLayer(email, password, data);
        }
    }
}
