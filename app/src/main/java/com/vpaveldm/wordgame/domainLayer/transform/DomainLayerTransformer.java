package com.vpaveldm.wordgame.domainLayer.transform;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.model.LoggingModelInDataLayer;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;
import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;

import javax.inject.Inject;

@ActivityScope
public class DomainLayerTransformer {

    @Inject
    DomainLayerTransformer() {
    }

    public LoggingModelInDomainLayer transform(LoggingModelInPresentationLayer model) {
        LoggingModelInDomainLayer.Builder builder = new LoggingModelInDomainLayer.Builder();
        builder.addData(model.getData())
                .addEmail(model.getEmail())
                .addPassword(model.getPassword());
        return builder.create();
    }

    public LoggingModelInDomainLayer transform(LoggingModelInDataLayer model){
        LoggingModelInDomainLayer.Builder builder = new LoggingModelInDomainLayer.Builder();
        builder.addPassword(model.getPassword())
                .addEmail(model.getEmail())
                .addData(model.getData());
        return builder.create();
    }

}
