package com.vpaveldm.wordgame.dataLayer.store.local;

import android.arch.paging.DataSource;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertCards(List<Card> cards);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertDeck(List<Deck> deck);

    @Query("SELECT * FROM decks")
    abstract Flowable<List<Deck>> getDecks();

    @Query("SELECT * FROM decks")
    abstract DataSource.Factory<Integer, Deck> getDeckDataSource();

    @Query("SELECT * FROM cards WHERE deck_id = :id")
    abstract List<Card> getCards(String id);

    @Query("SELECT * FROM decks WHERE id = :id")
    abstract Deck getDeckById(String id);

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

    public DataSource.Factory<Integer, Deck> getDeckWithCardsDataSource() {
        return getDeckDataSource().map(deck -> {
            deck.cards = getCards(deck.id);
            return deck;
        });
    }

    public Deck getDeck(String id) {
        Deck deck = getDeckById(id);
        deck.cards = getCards(deck.id);
        return deck;
    }
}
