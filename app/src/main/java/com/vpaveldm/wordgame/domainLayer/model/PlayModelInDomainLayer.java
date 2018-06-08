package com.vpaveldm.wordgame.domainLayer.model;

public class PlayModelInDomainLayer {
    private String deckName;
    private int wordCount;

    public PlayModelInDomainLayer(String deckName, int wordCount) {
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
