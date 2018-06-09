package com.vpaveldm.wordgame.dataLayer.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PlayModel {
    private String deckName;
    private int wordCount;

    public PlayModel() {
    }

    public PlayModel(String deckName, int wordCount) {
        this.deckName = deckName;
        this.wordCount = wordCount;
    }

    public String getDeckName() {
        return deckName;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }
}
