package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.domainLayer.interactors.PlayInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

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

    public Deck getDeck() {
        return mDeck;
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
                return null;
            } else { //If there isn't any cards
                return mInteractor.updateTopList(mDeck, time)
                        .subscribe(
                                () -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                                e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                        );
            }
        }
        //If answer isn't correct
        mMessageLiveData.setValue(new LiveDataMessage(true, INCORRECT_ANSWER));
        return null;
    }
}
