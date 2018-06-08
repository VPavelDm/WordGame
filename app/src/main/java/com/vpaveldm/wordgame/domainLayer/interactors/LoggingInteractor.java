package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.dataLayer.model.LoggingModel;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@ActivityScope
public class LoggingInteractor {

    private ILoggingRepository mRepository;

    @Inject
    LoggingInteractor(ILoggingRepository repository) {
        mRepository = repository;
    }

    public Observable<Boolean> signIn(LoggingModel model) {
        return mRepository.signIn(model);
    }

    public Observable<Boolean> signUp(LoggingModel model) {
        return mRepository.signUp(model);
    }

    public Single<LoggingModel> getGoogleIntent() {
        return Single.create(subscriber -> {
            LoggingModel model = mRepository.getGoogleIntent();
            subscriber.onSuccess(model);
        });
    }
}
