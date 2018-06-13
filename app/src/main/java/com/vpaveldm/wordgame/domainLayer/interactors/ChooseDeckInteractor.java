package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IChooseDeckRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

@ActivityScope
public class ChooseDeckInteractor {

    private final IChooseDeckRepository mRepository;

    @Inject
    ChooseDeckInteractor(IChooseDeckRepository repository) {
        mRepository = repository;
    }

    public Flowable<List<Deck>> getDecks() {
        return mRepository.getDecks();
    }

}
