package com.vpaveldm.wordgame.dataLayer.interfaces;

import io.reactivex.Completable;

public interface IPlayRepository {
    Completable startGame();
}
