package com.vpaveldm.wordgame.dataLayer.repository;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;

@ActivityScope
public class PlayRepositoryImpl implements IPlayRepository {

    @Inject
    PlayRepositoryImpl() {
    }

    @Override
    public Completable startGame() {
        return Completable.create(CompletableEmitter::onComplete).delay(5, TimeUnit.SECONDS);
    }
}
