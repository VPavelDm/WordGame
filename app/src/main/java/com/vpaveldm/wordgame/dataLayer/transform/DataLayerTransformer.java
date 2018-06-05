package com.vpaveldm.wordgame.dataLayer.transform;

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
        return new LoggingModelInDataLayer(model.getEmail(), model.getPassword());
    }
}
