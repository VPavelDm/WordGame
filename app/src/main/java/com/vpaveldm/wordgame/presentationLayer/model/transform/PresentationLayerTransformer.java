package com.vpaveldm.wordgame.presentationLayer.model.transform;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;
import com.vpaveldm.wordgame.domainLayer.model.PlayModelInDomainLayer;
import com.vpaveldm.wordgame.presentationLayer.model.logging.LoggingModelInPresentationLayer;
import com.vpaveldm.wordgame.presentationLayer.model.play.PlayModelInPresentationLayer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class PresentationLayerTransformer {

    @Inject
    PresentationLayerTransformer() {
    }

    public LoggingModelInPresentationLayer transform(LoggingModelInDomainLayer model) {
        LoggingModelInPresentationLayer.Builder builder = new LoggingModelInPresentationLayer.Builder();
        builder.addPassword(model.getPassword())
                .addEmail(model.getEmail())
                .addData(model.getData());
        return builder.create();
    }

    public List<PlayModelInPresentationLayer> transform(List<PlayModelInDomainLayer> domainModel) {
        List<PlayModelInPresentationLayer> presentationModel = new ArrayList<>();
        for (PlayModelInDomainLayer model: domainModel){
            presentationModel.add(new PlayModelInPresentationLayer(model.getDeckName(), model.getWordCount()));
        }
        return presentationModel;
    }
}
