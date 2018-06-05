package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.errors.IErrorListener;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;

public interface ILoggingRepository {
    void signIn(LoggingModelInDomainLayer model, IErrorListener listener);
    void signUp(LoggingModelInDomainLayer model, IErrorListener listener);
}
