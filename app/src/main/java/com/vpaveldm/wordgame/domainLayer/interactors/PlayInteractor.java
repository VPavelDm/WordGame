package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IFirebaseRepository;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class PlayInteractor {

    private final IPlayRepository mRepository;
    private final IFirebaseRepository mFirebaseRepository;

    @Inject
    PlayInteractor(IPlayRepository repository, IFirebaseRepository firebaseRepository) {
        mRepository = repository;
        mFirebaseRepository = firebaseRepository;
    }

    public Observable<Long> startGame() {
        return mRepository.startGame()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> updateTopList(Deck deck, long time) {
        return mFirebaseRepository.updateTopList(deck, time)
                .timeout(1, TimeUnit.SECONDS, Observable.error(new ConnectException("No internet connection")))
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Deck> getDeck(String id) {
        return mFirebaseRepository.getDeckById(id)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
