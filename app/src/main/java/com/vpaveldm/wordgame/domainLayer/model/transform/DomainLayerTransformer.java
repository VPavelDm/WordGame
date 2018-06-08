package com.vpaveldm.wordgame.domainLayer.model.transform;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.model.PlayModel;
import com.vpaveldm.wordgame.domainLayer.model.PlayModelInDomainLayer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class DomainLayerTransformer {

    @Inject
    DomainLayerTransformer() {
    }

    public List<PlayModelInDomainLayer> transform(List<PlayModel> domainList) {
        List<PlayModelInDomainLayer> playList = new ArrayList<>();
        for (PlayModel domainModel : domainList) {
            playList.add(new PlayModelInDomainLayer(domainModel.getDeckName(), domainModel.getWordCount()));
        }
        return playList;
    }

}
