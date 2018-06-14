package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IYandexTranslateRepository {
    Single<String> getAutoTranslateWord(String word);
}
