package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IAddDeckRepository {
    Completable addDeck(Deck model);
    Single<String> getAutoTranslateWord(String word);
}
