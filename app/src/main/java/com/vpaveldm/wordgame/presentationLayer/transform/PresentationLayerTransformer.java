package com.vpaveldm.wordgame.presentationLayer.transform;

import android.content.Intent;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;

import javax.inject.Inject;

@ActivityScope
public class PresentationLayerTransformer {

    @Inject
    PresentationLayerTransformer() {
    }

    public LoggingModelInPresentationLayer getLoggingModel(String email, String password) {
        LoggingModelInPresentationLayer.Builder builder = new LoggingModelInPresentationLayer.Builder();
        builder.addEmail(email);
        builder.addPassword(password);
        return builder.create();
    }

    public LoggingModelInPresentationLayer getLoggingModel(Intent data) {
        LoggingModelInPresentationLayer.Builder builder = new LoggingModelInPresentationLayer.Builder();
        builder.addData(data);
        return builder.create();
    }

    public LoggingModelInPresentationLayer getLoggingModel(){
        LoggingModelInPresentationLayer.Builder builder = new LoggingModelInPresentationLayer.Builder();
        return builder.create();
    }

}
