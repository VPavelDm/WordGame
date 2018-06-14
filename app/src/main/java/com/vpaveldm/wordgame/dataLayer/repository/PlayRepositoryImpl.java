package com.vpaveldm.wordgame.dataLayer.repository;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class PlayRepositoryImpl implements IPlayRepository {

    @Inject
    PlayRepositoryImpl() {
    }

    @Override
    public Observable<Long> startGame() {
        return Observable.interval(1, TimeUnit.SECONDS);
    }
}
