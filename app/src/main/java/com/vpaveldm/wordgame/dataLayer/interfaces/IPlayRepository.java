package com.vpaveldm.wordgame.dataLayer.interfaces;

import com.vpaveldm.wordgame.dataLayer.model.PlayModel;

import java.util.List;

import io.reactivex.Single;

public interface IPlayRepository {
    Single<List<PlayModel>> getDecks();
}
