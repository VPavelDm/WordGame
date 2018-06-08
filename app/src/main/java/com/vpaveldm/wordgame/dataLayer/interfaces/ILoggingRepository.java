package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.model.LoggingModel;

import io.reactivex.Completable;

public interface ILoggingRepository {
    Completable signIn(LoggingModel model);
    Completable signUp(LoggingModel model);
    LoggingModel getGoogleIntent();
}
