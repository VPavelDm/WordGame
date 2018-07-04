package com.vpaveldm.wordgame.dataLayer.repository;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Pavel Vaitsikhouski
 */
@ActivityScope
public class PlayRepositoryImpl implements IPlayRepository {

    /**
     * Construct empty object
     */
    @Inject
    PlayRepositoryImpl() {
    }

    /**
     * Start stopwatch
     *
     * @return Observable that sends seconds
     */
    @Override
    public Observable<Long> startGame() {
        return Observable.interval(1, TimeUnit.SECONDS);
    }
}
