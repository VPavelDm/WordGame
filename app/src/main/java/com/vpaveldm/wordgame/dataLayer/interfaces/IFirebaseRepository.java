package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.dataLayer.store.model.TopUserList;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IFirebaseRepository {
    Flowable<List<Deck>> getDecks();
    Single<Deck> getDeckById(String id);
    Completable addDeck(Deck model);
    Observable<Boolean> updateTopList(Deck deck, long time);
    Observable<TopUserList> getTopList(Deck deck);
}
