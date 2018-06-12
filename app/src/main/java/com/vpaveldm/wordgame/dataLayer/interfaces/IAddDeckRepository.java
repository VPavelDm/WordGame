package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.model.Deck;

import io.reactivex.Completable;

public interface IAddDeckRepository {
    Completable addDeck(Deck model);
}
