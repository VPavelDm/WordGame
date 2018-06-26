package com.vpaveldm.wordgame.dataLayer.store.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class TopUserList {
    public String deckName;
    public final List<User> users = new ArrayList<>();
}
