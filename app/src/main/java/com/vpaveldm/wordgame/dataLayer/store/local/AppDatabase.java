package com.vpaveldm.wordgame.dataLayer.store.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

@Database(entities = {Deck.class, Card.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DeckDao deckDao();
}
