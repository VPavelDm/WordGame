package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.model.PlayModelInDataLayer;
import com.vpaveldm.wordgame.domainLayer.model.PlayModelInDomainLayer;

import java.util.List;

import io.reactivex.Single;

public interface IPlayRepository {
    Single<List<PlayModelInDataLayer>> getDecks();
}
