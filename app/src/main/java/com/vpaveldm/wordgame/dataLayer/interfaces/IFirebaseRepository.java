package com.vpaveldm.wordgame.dataLayer.interfaces;

import android.arch.paging.DataSource;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.dataLayer.store.model.TopUserList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IFirebaseRepository {
    Single<Deck> getDeckById(String id);
    Completable addDeck(Deck model);
    Observable<Boolean> updateTopList(Deck deck, long time);
    Observable<TopUserList> getTopList(Deck deck);
    Completable subscribeOnUpdate();
    DataSource.Factory<Integer, Deck> getDecks();
}
