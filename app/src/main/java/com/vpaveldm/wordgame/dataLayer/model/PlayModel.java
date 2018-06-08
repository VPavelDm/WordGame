package com.vpaveldm.wordgame.dataLayer.model;

public class PlayModel {
    private String deckName;
    private int wordCount;

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
}
