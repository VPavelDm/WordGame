package com.vpaveldm.wordgame.dataLayer.model.transform;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.model.LoggingModelInDataLayer;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;

import javax.inject.Inject;

@ActivityScope
public class DataLayerTransformer {

    @Inject
    DataLayerTransformer() {
    }

    public LoggingModelInDataLayer transform(LoggingModelInDomainLayer model){
        LoggingModelInDataLayer.Builder builder = new LoggingModelInDataLayer.Builder();
        builder.addData(model.getData())
                .addEmail(model.getEmail())
                .addPassword(model.getPassword());
        return builder.create();
    }
}
