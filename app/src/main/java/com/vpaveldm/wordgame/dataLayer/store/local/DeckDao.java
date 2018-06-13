package com.vpaveldm.wordgame.dataLayer.store.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class DeckDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertCards(List<Card> cards);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertDeck(List<Deck> deck);

    @Query("SELECT * FROM decks")
    abstract Flowable<List<Deck>> getDecks();

    @Query("SELECT * FROM cards WHERE deck_id = :id")
    abstract List<Card> getCards(String id);

    @Transaction
    public void insertDecksWithCards(List<Deck> decks) {
        for (int i = 0; i < decks.size(); i++) {
            List<Card> cards = decks.get(i).cards;
            insertCards(cards);
        }
        insertDeck(decks);
    }

    public Flowable<List<Deck>> getDecksWithCards() {
        Flowable<List<Deck>> decksFlowable = getDecks();
        return decksFlowable.map(decks -> {
            for (Deck deck : decks) {
                deck.cards = getCards(deck.id);
            }
            return decks;
        });
    }
}
