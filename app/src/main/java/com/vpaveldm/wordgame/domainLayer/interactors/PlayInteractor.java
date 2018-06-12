package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;
import com.vpaveldm.wordgame.dataLayer.model.Deck;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class PlayInteractor {

    private IPlayRepository mRepository;

    @Inject
    PlayInteractor(IPlayRepository repository) {
        mRepository = repository;
    }

    public Observable<List<Deck>> getDecks() {
        return mRepository.getDecks();
    }

}
