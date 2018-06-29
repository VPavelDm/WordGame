package com.vpaveldm.wordgame.domainLayer.interactors;

import android.arch.paging.DataSource;
import android.util.Pair;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IFirebaseRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@ActivityScope
public class ChooseDeckInteractor {

    private final IFirebaseRepository mRepository;

    @Inject
    ChooseDeckInteractor(IFirebaseRepository repository) {
        mRepository = repository;
    }

    public Flowable<List<Deck>> getDecks() {
        return mRepository.getDecks();
    }

    public Pair<Completable, DataSource.Factory<Integer, Deck>> getDeckDataSource() {
        return new Pair<>(
                mRepository.subscribeOnUpdate(),
                mRepository.getDeckDataSource());
    }

}
