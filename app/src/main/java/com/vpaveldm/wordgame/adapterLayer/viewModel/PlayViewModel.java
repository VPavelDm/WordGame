package com.vpaveldm.wordgame.adapterLayer.viewModel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.domainLayer.interactors.PlayInteractor;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class PlayViewModel extends ViewModel {
    @Inject
    PlayInteractor mInteractor;
    public static final String INCORRECT_ANSWER = "Incorrect answer";

    private Deck mDeck;
    private int currentCard = 0;
    private MutableLiveData<Card> mCardLiveData;
    private MutableLiveData<LiveDataMessage> mMessageLiveData;
    private long time = 0L;

    public PlayViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void createLiveDataAndSubscribe(LifecycleOwner owner,
                                           Observer<Card> cardObserver,
                                           Observer<LiveDataMessage> messageObserver) {
        mCardLiveData = new MutableLiveData<>();
        mMessageLiveData = new MutableLiveData<>();

        mCardLiveData.observe(owner, cardObserver);
        mMessageLiveData.observe(owner, messageObserver);
    }

    @SuppressLint("CheckResult")
    public void getDeck(String id) {
        mInteractor.getDeck(id)
                .subscribe(deck -> {
                    mDeck = deck;
                    mCardLiveData.setValue(mDeck.cards.get(currentCard));
                });
    }

    public Disposable startGame() {
        return mInteractor.startGame().subscribe(t -> ++time);
    }

    public Disposable checkAnswer(String answer) {
        Card card = mDeck.cards.get(currentCard);
        if (card.translate.equals(answer)) { //If answer is correct turn next card
            ++currentCard;
            if (currentCard != mDeck.cards.size()) {
                mCardLiveData.setValue(mDeck.cards.get(currentCard));
                return null;
            } else { //If there isn't any cards
                return mInteractor.updateTopList(mDeck, time)
                        .subscribe(
                                ans -> {
                                },
                                e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage())),
                                () -> mMessageLiveData.setValue(new LiveDataMessage(true, null))
                        );
            }
        }
        //If answer isn't correct
        mMessageLiveData.setValue(new LiveDataMessage(true, INCORRECT_ANSWER));
        return null;
    }
}
