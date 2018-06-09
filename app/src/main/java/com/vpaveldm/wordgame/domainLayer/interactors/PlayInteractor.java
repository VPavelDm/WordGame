package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;
import com.vpaveldm.wordgame.dataLayer.model.PlayModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

@ActivityScope
public class PlayInteractor {

    private IPlayRepository mRepository;

    @Inject
    PlayInteractor(IPlayRepository repository) {
        mRepository = repository;
    }

    public Observable<List<PlayModel>> getDecks() {
        return mRepository.getDecks();
    }

    public Completable addDeck(PlayModel playModel) {
        return mRepository.addDeck(playModel);
    }

}
