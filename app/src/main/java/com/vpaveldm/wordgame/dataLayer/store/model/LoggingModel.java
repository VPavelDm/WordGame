package com.vpaveldm.wordgame.dataLayer.store.model;

import android.content.Intent;

public class LoggingModel {
    public String email;
    public String password;
    public Intent data;

    public LoggingModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoggingModel(Intent data) {
        this.data = data;
    }
}
