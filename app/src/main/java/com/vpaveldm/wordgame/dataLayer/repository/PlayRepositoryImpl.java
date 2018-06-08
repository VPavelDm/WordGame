package com.vpaveldm.wordgame.dataLayer.repository;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;
import com.vpaveldm.wordgame.dataLayer.model.PlayModelInDataLayer;
import com.vpaveldm.wordgame.domainLayer.model.PlayModelInDomainLayer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

@ActivityScope
public class PlayRepositoryImpl implements IPlayRepository {

    @Inject
    PlayRepositoryImpl() {
    }

    @Override
    public Single<List<PlayModelInDataLayer>> getDecks() {
        return Single.create(subscriber -> {
            List<PlayModelInDataLayer> list = new ArrayList<>();
            list.add(new PlayModelInDataLayer("Animals", 10));
            list.add(new PlayModelInDataLayer("People", 13));
            list.add(new PlayModelInDataLayer("Cities", 17));
            list.add(new PlayModelInDataLayer("Countries", 23));
            list.add(new PlayModelInDataLayer("Days", 123));
            subscriber.onSuccess(list);
        });
    }
}
