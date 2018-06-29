package com.vpaveldm.wordgame.domainLayer.interactors;

import android.arch.paging.DataSource;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IFirebaseRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class ChooseDeckInteractor {

    private final IFirebaseRepository mRepository;

    @Inject
    ChooseDeckInteractor(IFirebaseRepository repository) {
        mRepository = repository;
    }

    public DataSource.Factory<Integer, Deck> getDeckDataSource() {
        return mRepository.getDecks();
    }

    public Completable subscribeOnUpdate() {
        return mRepository.subscribeOnUpdate()
                .timeout(2, TimeUnit.SECONDS, Completable.error(new ConnectException("Can not update decks")))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
