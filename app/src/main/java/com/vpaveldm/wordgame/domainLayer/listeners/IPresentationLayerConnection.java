package com.vpaveldm.wordgame.domainLayer.listeners;

import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;

public interface IPresentationLayerConnection {
    void result(LoggingModelInPresentationLayer model);
}
