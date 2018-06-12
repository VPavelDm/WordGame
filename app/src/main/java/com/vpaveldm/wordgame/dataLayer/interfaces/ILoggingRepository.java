package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.store.model.LoggingModel;

import io.reactivex.Observable;

public interface ILoggingRepository {
    Observable<Boolean> signIn(LoggingModel model);
    Observable<Boolean> signUp(LoggingModel model);
    LoggingModel getGoogleIntent();
}
