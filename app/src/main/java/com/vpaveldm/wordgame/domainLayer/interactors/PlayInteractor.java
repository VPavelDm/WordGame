package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;
import com.vpaveldm.wordgame.dataLayer.model.PlayModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

@ActivityScope
public class PlayInteractor {

    private IPlayRepository mRepository;

    @Inject
    PlayInteractor(IPlayRepository repository) {
        mRepository = repository;
    }

    public Single<List<PlayModel>> getDecks() {
        return mRepository.getDecks();
    }

}
