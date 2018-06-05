package com.vpaveldm.wordgame.domainLayer.transform;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;
import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;

import javax.inject.Inject;

@ActivityScope
public class DomainLayerTransformer {

    @Inject
    DomainLayerTransformer() {
    }

    public LoggingModelInDomainLayer transform(LoggingModelInPresentationLayer model) {
        return new LoggingModelInDomainLayer(model.getEmail(), model.getPassword());
    }

}
