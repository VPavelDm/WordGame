package com.vpaveldm.wordgame.presentationLayer.model.transform;

import android.content.Intent;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;
import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;

import javax.inject.Inject;

@ActivityScope
public class PresentationLayerTransformer {

    @Inject
    PresentationLayerTransformer() {
    }

    public LoggingModelInPresentationLayer transform(LoggingModelInDomainLayer model){
        LoggingModelInPresentationLayer.Builder builder = new LoggingModelInPresentationLayer.Builder();
        builder.addPassword(model.getPassword())
                .addEmail(model.getEmail())
                .addData(model.getData());
        return builder.create();
    }
}
