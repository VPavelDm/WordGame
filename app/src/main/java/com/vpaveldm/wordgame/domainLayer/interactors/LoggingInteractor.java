package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.dataLayer.model.LoggingModelInDataLayer;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;
import com.vpaveldm.wordgame.domainLayer.model.transform.DomainLayerTransformer;
import com.vpaveldm.wordgame.presentationLayer.model.logging.LoggingModelInPresentationLayer;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

@ActivityScope
public class LoggingInteractor {

    private DomainLayerTransformer mTransformer;
    private ILoggingRepository mRepository;

    @Inject
    LoggingInteractor(DomainLayerTransformer transformer, ILoggingRepository repository) {
        mTransformer = transformer;
        mRepository = repository;
    }

    public Completable signIn(LoggingModelInPresentationLayer model) {
        LoggingModelInDomainLayer domainModel = mTransformer.transform(model);
        return mRepository.signIn(domainModel);
    }

    public Completable signUp(LoggingModelInPresentationLayer model) {
        LoggingModelInDomainLayer domainModel = mTransformer.transform(model);
        return mRepository.signUp(domainModel);
    }

    public Single<LoggingModelInDomainLayer> getGoogleIntent() {
        return Single.create(subscriber -> {
            LoggingModelInDataLayer modelInDataLayer = mRepository.getGoogleIntent();
            subscriber.onSuccess(mTransformer.transform(modelInDataLayer));
        });
    }
}
