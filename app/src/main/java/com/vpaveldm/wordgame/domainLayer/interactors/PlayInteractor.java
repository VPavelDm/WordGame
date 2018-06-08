package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;
import com.vpaveldm.wordgame.domainLayer.model.PlayModelInDomainLayer;
import com.vpaveldm.wordgame.domainLayer.model.transform.DomainLayerTransformer;
import com.vpaveldm.wordgame.presentationLayer.model.play.PlayModelInPresentationLayer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

@ActivityScope
public class PlayInteractor {

    private DomainLayerTransformer mTransformer;
    private IPlayRepository mRepository;

    @Inject
    PlayInteractor(DomainLayerTransformer transformer, IPlayRepository repository) {
        mTransformer = transformer;
        mRepository = repository;
    }

    public Single<List<PlayModelInDomainLayer>> getDecks() {
        return mRepository.getDecks().map(model -> mTransformer.transform(model));
    }

}
