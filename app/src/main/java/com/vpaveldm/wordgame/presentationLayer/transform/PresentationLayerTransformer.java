package com.vpaveldm.wordgame.presentationLayer.transform;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;

import javax.inject.Inject;

@ActivityScope
public class PresentationLayerTransformer {

    @Inject
    PresentationLayerTransformer() {
    }

    public LoggingModelInPresentationLayer getLoggingModelInPresentationLayer(
            String email, String password
    ) {
        return new LoggingModelInPresentationLayer(email, password);
    }

}
