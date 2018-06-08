package com.vpaveldm.wordgame.presentationLayer.model.play;

import java.util.List;

public class PlayModelInPresentationLayer {
    private String deckName;
    private int wordCount;

    public PlayModelInPresentationLayer(String deckName, int wordCount) {
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
