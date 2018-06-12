package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IAddDeckRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

@ActivityScope
public class AddDeckInteractor {

    private final IAddDeckRepository mRepository;

    @Inject
    AddDeckInteractor(IAddDeckRepository repository) {
        mRepository = repository;
    }

    public Completable addDeck(Deck deck) {
        return mRepository.addDeck(deck);
    }

    public Single<String> getAutoTranslate(String word) {
        return mRepository.getAutoTranslateWord(word);
    }
}
