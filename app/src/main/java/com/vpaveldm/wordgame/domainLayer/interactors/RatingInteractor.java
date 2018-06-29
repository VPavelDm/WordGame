package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IFirebaseRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.dataLayer.store.model.TopUserList;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class RatingInteractor {
    private final IFirebaseRepository mRepository;

    @Inject
    RatingInteractor(IFirebaseRepository repository) {
        mRepository = repository;
    }

    public Observable<TopUserList> getTopUsers(Deck deck) {
        return mRepository.getTopList(deck)
                .timeout(1, TimeUnit.SECONDS, Observable.error(new ConnectException("No internet connection")))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single());
    }

    public Single<Deck> getDeck(String id) {
        return mRepository.getDeckById(id)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
