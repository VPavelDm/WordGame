package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.domainLayer.interactors.AddDeckInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import javax.inject.Inject;

public class AddDeckViewModel extends ViewModel {

    @Inject
    AddDeckInteractor mAddDeckInteractor;
    private final Deck mDeck;
    private MutableLiveData<LiveDataMessage> mDeckLiveData;
    private MutableLiveData<LiveDataMessage> mTranslateLiveData;

    public AddDeckViewModel() {
        mDeck = new Deck();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribeOnDeckLiveData(LifecycleOwner owner, Observer<LiveDataMessage> listener) {
        if (mDeckLiveData == null) {
            mDeckLiveData = new MutableLiveData<>();
        }
        mDeckLiveData.observe(owner, listener);
    }

    public void subscribeOnTranslateLiveData(LifecycleOwner owner, Observer<LiveDataMessage> listener) {
        if (mTranslateLiveData == null) {
            mTranslateLiveData = new MutableLiveData<>();
        }
        mTranslateLiveData.observe(owner, listener);
    }

    public void addCard(Card card) {
        mDeck.cards.add(card);
    }

    @SuppressLint("CheckResult") //addDeck returns Completable
    public void createDeck(String name) {
        mDeck.deckName = name;
        mAddDeckInteractor.addDeck(mDeck)
                .subscribe(
                        () -> mDeckLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mDeckLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
    }

    public int getCardSize() {
        return mDeck.cards.size();
    }

    @SuppressLint("CheckResult") //It returns Single
    public void getAutoTranslate(String word) {
        mAddDeckInteractor.getAutoTranslate(word).subscribe(
                success -> mTranslateLiveData.setValue(new LiveDataMessage(true, success)),
                e -> mTranslateLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
        );
    }
}
