package com.vpaveldm.wordgame.domainLayer.model;

import android.content.Intent;

public class LoggingModelInDomainLayer {
    private String email;
    private String password;
    private Intent data;

    private LoggingModelInDomainLayer(String email, String password, Intent data) {
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

        public LoggingModelInDomainLayer.Builder addEmail(String email) {
            this.email = email;
            return this;
        }

        public LoggingModelInDomainLayer.Builder addPassword(String password) {
            this.password = password;
            return this;
        }

        public LoggingModelInDomainLayer.Builder addData(Intent data) {
            this.data = data;
            return this;
        }

        public LoggingModelInDomainLayer create() {
            return new LoggingModelInDomainLayer(email, password, data);
        }
    }
}
