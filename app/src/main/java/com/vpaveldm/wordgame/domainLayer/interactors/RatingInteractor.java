package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IFirebaseRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.dataLayer.store.model.TopUserList;

import javax.inject.Inject;

import io.reactivex.Observable;
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single());
    }
}
