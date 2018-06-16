package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.store.model.LoggingModel;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface ILoggingRepository {
    Observable<Boolean> signIn(LoggingModel model);
    Observable<Boolean> signUp(LoggingModel model);
    Single<LoggingModel> getGoogleIntent();
}
