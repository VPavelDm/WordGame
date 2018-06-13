package com.vpaveldm.wordgame.dataLayer.store.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
@Entity(tableName = "decks")
public class Deck {
    @PrimaryKey
    @NonNull
    public String id = "";
    public String deckName;
    @Ignore
    public List<Card> cards = new ArrayList<>();
}

