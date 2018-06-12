package com.vpaveldm.wordgame.dataLayer.store.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Deck {
    private String deckName;
    private List<Card> mCards;

    public Deck() {
        super();
        mCards = new ArrayList<>();
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public List<Card> getCards() {
        return mCards;
    }

    public void setCards(List<Card> cards) {
        mCards = cards;
    }
}
