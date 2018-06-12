package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.domainLayer.interactors.AddDeckInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.dataLayer.model.Card;
import com.vpaveldm.wordgame.dataLayer.model.Deck;

import javax.inject.Inject;

public class AddDeckViewModel extends ViewModel {

    @Inject
    AddDeckInteractor mAddDeckInteractor;
    private final Deck mDeck;
    private MutableLiveData<LiveDataMessage> mMessageLiveData;

    public AddDeckViewModel() {
        mDeck = new Deck();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribeOnMessageLiveData(LifecycleOwner owner, Observer<LiveDataMessage> listener) {
        if (mMessageLiveData == null) {
            mMessageLiveData = new MutableLiveData<>();
        }
        mMessageLiveData.observe(owner, listener);
    }

    public void addCard(Card card) {
        mDeck.getCards().add(card);
    }

    @SuppressLint("CheckResult") //addDeck returns Completable
    public void createDeck(String name) {
        mDeck.setDeckName(name);
        mAddDeckInteractor.addDeck(mDeck)
                .subscribe(
                        () -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
    }

    public int getCardSize() {
        return mDeck.getCards().size();
    }

}
