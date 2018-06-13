package com.vpaveldm.wordgame.dataLayer.store.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
@Entity(tableName = "cards")
public class Card {
    @PrimaryKey
    @NonNull
    public String id = "";
    public String word;
    public String translate;
    @TypeConverters({CardConverter.class})
    public List<String> wrongTranslates = new ArrayList<>();
    public String deck_id;
}
