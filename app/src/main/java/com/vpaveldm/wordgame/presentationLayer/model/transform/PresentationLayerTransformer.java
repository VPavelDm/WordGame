package com.vpaveldm.wordgame.presentationLayer.model.transform;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.domainLayer.model.PlayModelInDomainLayer;
import com.vpaveldm.wordgame.presentationLayer.model.play.PlayModelInPresentationLayer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class PresentationLayerTransformer {

    @Inject
    PresentationLayerTransformer() {
    }

    public List<PlayModelInPresentationLayer> transform(List<PlayModelInDomainLayer> domainModel) {
        List<PlayModelInPresentationLayer> presentationModel = new ArrayList<>();
        for (PlayModelInDomainLayer model: domainModel){
            presentationModel.add(new PlayModelInPresentationLayer(model.getDeckName(), model.getWordCount()));
        }
        return presentationModel;
    }
}
