package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.domainLayer.interactors.PlayInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

public class PlayViewModel extends ViewModel {
    @Inject
    PlayInteractor mInteractor;
    private Deck mDeck;
    private int currentCard = 0;
    private MutableLiveData<Card> mCardLiveData;
    private MutableLiveData<LiveDataMessage> mMessageLiveData;

    public PlayViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribe(LifecycleOwner owner, Observer<Card> cardObserver, Observer<LiveDataMessage> messageObserver) {
        if (mCardLiveData == null) {
            mCardLiveData = new MutableLiveData<>();
        }
        mCardLiveData.observe(owner, cardObserver);
        if (mMessageLiveData == null) {
            mMessageLiveData = new MutableLiveData<>();
        }
        mMessageLiveData.observe(owner, messageObserver);
    }

    @SuppressLint("CheckResult")
    public void startGame(Deck deck) {
        mDeck = deck;
        mInteractor.startGame().subscribe(
                () -> mMessageLiveData.setValue(new LiveDataMessage(false, "Time out!")));
        mCardLiveData.setValue(mDeck.cards.get(currentCard));
    }

    public void checkAnswer(String answer) {
        Card card = mDeck.cards.get(currentCard);
        if (card.translate.equals(answer)) {
            ++currentCard;
            if (currentCard != mDeck.cards.size()) {
                mCardLiveData.setValue(mDeck.cards.get(currentCard));
            } else {
                mMessageLiveData.setValue(new LiveDataMessage(true, null));
            }
        } else {
            mMessageLiveData.setValue(new LiveDataMessage(false, String.valueOf(currentCard)));
        }
    }
}
