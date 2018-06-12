package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.model.Deck;

import java.util.List;

import io.reactivex.Observable;

public interface IPlayRepository {
    Observable<List<Deck>> getDecks();
}
