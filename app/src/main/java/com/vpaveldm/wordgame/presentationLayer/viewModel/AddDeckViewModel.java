package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.vpaveldm.wordgame.domainLayer.interactors.AddDeckInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class AddDeckViewModel extends AbstractViewModel {

    @Inject
    AddDeckInteractor mAddDeckInteractor;
    private final Deck mDeck;
    private MutableLiveData<LiveDataMessage> mMessageLiveData;
    private MutableLiveData<LiveDataMessage> mTranslateLiveData;

    public AddDeckViewModel() {
        mDeck = new Deck();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribe(LifecycleOwner owner,
                          Observer<LiveDataMessage> messageListener,
                          Observer<LiveDataMessage> translateListener) {
        if (mMessageLiveData == null) {
            mMessageLiveData = new MutableLiveData<>();
        }
        mMessageLiveData.observe(owner, messageListener);
        if (mTranslateLiveData == null) {
            mTranslateLiveData = new MutableLiveData<>();
        }
        mTranslateLiveData.observe(owner, translateListener);
    }

    public void addCard(Card card) {
        mDeck.cards.add(card);
    }

    public Disposable createDeck(String name) {
        mDeck.deckName = name;
        return mAddDeckInteractor.addDeck(mDeck)
                .subscribe(
                        () -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
    }

    public int getCardSize() {
        return mDeck.cards.size();
    }

    public Disposable getAutoTranslate(String word) {
        return mAddDeckInteractor.getAutoTranslate(word).subscribe(
                success -> mTranslateLiveData.setValue(new LiveDataMessage(true, success)),
                e -> mTranslateLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTranslateLiveData = null;
        mMessageLiveData = null;
    }
}
