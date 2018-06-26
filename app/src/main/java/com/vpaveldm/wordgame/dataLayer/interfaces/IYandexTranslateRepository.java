package com.vpaveldm.wordgame.dataLayer.interfaces;

import io.reactivex.Single;

public interface IYandexTranslateRepository {
    Single<String> getAutoTranslateWord(String word);
}
