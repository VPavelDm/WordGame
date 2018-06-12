package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IAddDeckRepository;
import com.vpaveldm.wordgame.dataLayer.model.Deck;

import javax.inject.Inject;

import io.reactivex.Completable;

@ActivityScope
public class AddDeckInteractor {

    private IAddDeckRepository mRepository;

    @Inject
    AddDeckInteractor(IAddDeckRepository repository) {
        mRepository = repository;
    }

    public Completable addDeck(Deck deck) {
        return mRepository.addDeck(deck);
    }
}
