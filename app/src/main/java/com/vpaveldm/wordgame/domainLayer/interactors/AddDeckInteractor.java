package com.vpaveldm.wordgame.domainLayer.interactors;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IFirebaseRepository;
import com.vpaveldm.wordgame.dataLayer.interfaces.IYandexTranslateRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class AddDeckInteractor {

    private final IYandexTranslateRepository mYandexTranslateRepository;
    private final IFirebaseRepository mFirebaseRepository;

    @Inject
    AddDeckInteractor(IYandexTranslateRepository repository, IFirebaseRepository firebaseRepository) {
        mYandexTranslateRepository = repository;
        mFirebaseRepository = firebaseRepository;
    }

    public Completable addDeck(Deck deck) {
        return mFirebaseRepository.addDeck(deck)
                .timeout(1, TimeUnit.SECONDS, Completable.error(new ConnectException("No internet connection")))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    public Single<String> getAutoTranslate(String word) {
        return mYandexTranslateRepository.getAutoTranslateWord(word);
    }
}
