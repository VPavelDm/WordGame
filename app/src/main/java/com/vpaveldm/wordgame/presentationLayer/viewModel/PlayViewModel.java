package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.domainLayer.interactors.PlayInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class PlayViewModel extends ViewModel {
    @Inject
    PlayInteractor mInteractor;
    private Deck mDeck;
    private int currentCard = 0;
    private MutableLiveData<Card> mCardLiveData;
    private MutableLiveData<LiveDataMessage> mMessageLiveData;
    private long time;

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

    public Disposable startGame(Deck deck) {
        mDeck = deck;
        Disposable d = mInteractor.startGame().subscribe(t -> time = t);
        mCardLiveData.setValue(mDeck.cards.get(currentCard));
        return d;
    }

    public Disposable checkAnswer(String answer) {
        Card card = mDeck.cards.get(currentCard);
        if (card.translate.equals(answer)) { //If answer is correct turn next card
            ++currentCard;
            if (currentCard != mDeck.cards.size()) {
                mCardLiveData.setValue(mDeck.cards.get(currentCard));
            } else { //If there isn't any cards
                Disposable d = mInteractor.updateTopList(mDeck, time).subscribe(
                        () -> {
                        },
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
                mMessageLiveData.setValue(new LiveDataMessage(true, null));
                return d;
            }
        } else { //If answer isn't correct
            mMessageLiveData.setValue(new LiveDataMessage(false, String.valueOf(currentCard)));
        }
        return null;
    }
}
