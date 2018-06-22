package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.LoggingModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class LoggingInteractor {

    private final ILoggingRepository mRepository;

    @Inject
    LoggingInteractor(ILoggingRepository repository) {
        mRepository = repository;
    }

    public Observable<Boolean> signIn(LoggingModel model) {
        return mRepository
                .signIn(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> signUp(LoggingModel model) {
        return mRepository
                .signUp(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<LoggingModel> getGoogleIntent() {
        return mRepository
                .getGoogleIntent()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
