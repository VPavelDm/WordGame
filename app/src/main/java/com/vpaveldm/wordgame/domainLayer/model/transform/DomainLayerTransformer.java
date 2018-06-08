package com.vpaveldm.wordgame.domainLayer.model.transform;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.model.LoggingModelInDataLayer;
import com.vpaveldm.wordgame.dataLayer.model.PlayModelInDataLayer;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;
import com.vpaveldm.wordgame.domainLayer.model.PlayModelInDomainLayer;
import com.vpaveldm.wordgame.presentationLayer.model.logging.LoggingModelInPresentationLayer;
import com.vpaveldm.wordgame.presentationLayer.model.play.PlayModelInPresentationLayer;

import java.util.ArrayList;
import java.util.List;

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

    public LoggingModelInDomainLayer transform(LoggingModelInDataLayer model) {
        LoggingModelInDomainLayer.Builder builder = new LoggingModelInDomainLayer.Builder();
        builder.addPassword(model.getPassword())
                .addEmail(model.getEmail())
                .addData(model.getData());
        return builder.create();
    }

    public List<PlayModelInDomainLayer> transform(List<PlayModelInDataLayer> domainList) {
        List<PlayModelInDomainLayer> playList = new ArrayList<>();
        for (PlayModelInDataLayer domainModel : domainList) {
            playList.add(new PlayModelInDomainLayer(domainModel.getDeckName(), domainModel.getWordCount()));
        }
        return playList;
    }

}
