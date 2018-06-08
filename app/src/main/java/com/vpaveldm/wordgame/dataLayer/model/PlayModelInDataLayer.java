package com.vpaveldm.wordgame.dataLayer.model;

public class PlayModelInDataLayer {
    private String deckName;
    private int wordCount;

    public PlayModelInDataLayer(String deckName, int wordCount) {
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
