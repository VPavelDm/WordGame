package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.errors.IErrorListener;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;
import com.vpaveldm.wordgame.domainLayer.transform.DomainLayerTransformer;
import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;

import javax.inject.Inject;

@ActivityScope
public class LoggingInteractor {

    private DomainLayerTransformer mTransformer;
    private ILoggingRepository mRepository;

    @Inject
    LoggingInteractor(DomainLayerTransformer transformer, ILoggingRepository repository) {
        mTransformer = transformer;
        mRepository = repository;
    }

    public void signIn(final LoggingModelInPresentationLayer model, final IErrorListener listener) {
        LoggingModelInDomainLayer domainModel = mTransformer.transform(model);
        mRepository.signIn(domainModel, new IErrorListener() {
            @Override
            public void success() {
                listener.success();
            }

            @Override
            public void failure(String message) {
                listener.failure(message);
            }
        });
    }

    public void signUp(LoggingModelInPresentationLayer model, final IErrorListener listener){
        LoggingModelInDomainLayer domainModel = mTransformer.transform(model);
        mRepository.signUp(domainModel, new IErrorListener() {
            @Override
            public void success() {
                listener.success();
            }

            @Override
            public void failure(String message) {
                listener.failure(message);
            }
        });
    }
}
