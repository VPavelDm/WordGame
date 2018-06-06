package com.vpaveldm.wordgame.dataLayer.interfaces;

import android.content.Intent;

import com.vpaveldm.wordgame.dataLayer.model.LoggingModelInDataLayer;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;

import io.reactivex.Single;

public interface ILoggingRepository {
    Single<Boolean> signIn(LoggingModelInDomainLayer model);
    Single<Boolean> signUp(LoggingModelInDomainLayer model);
    LoggingModelInDataLayer getGoogleIntent();
}
