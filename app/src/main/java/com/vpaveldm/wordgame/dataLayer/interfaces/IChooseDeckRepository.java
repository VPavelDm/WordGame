package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.util.List;

import io.reactivex.Flowable;

public interface IChooseDeckRepository {
    Flowable<List<Deck>> getDecks();
}
