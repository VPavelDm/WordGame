package com.vpaveldm.wordgame.dataLayer.interfaces;

import io.reactivex.Observable;

public interface IPlayRepository {
    Observable<Long> startGame();
}
