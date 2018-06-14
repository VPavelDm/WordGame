package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface IFirebaseRepository {
    Flowable<List<Deck>> getDecks();
    Completable addDeck(Deck model);
    Completable updateTopList(Deck deck, long time);
}
