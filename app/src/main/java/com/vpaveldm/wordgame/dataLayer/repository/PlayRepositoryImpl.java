package com.vpaveldm.wordgame.dataLayer.repository;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;
import com.vpaveldm.wordgame.dataLayer.model.PlayModel;

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
    public Single<List<PlayModel>> getDecks() {
        return Single.create(subscriber -> {
            List<PlayModel> list = new ArrayList<>();
            list.add(new PlayModel("Animals", 10));
            list.add(new PlayModel("People", 13));
            list.add(new PlayModel("Cities", 17));
            list.add(new PlayModel("Countries", 23));
            list.add(new PlayModel("Days", 123));
            subscriber.onSuccess(list);
        });
    }
}
