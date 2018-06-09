package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.model.PlayModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface IPlayRepository {
    Observable<List<PlayModel>> getDecks();
    Completable addDeck(PlayModel model);
}
