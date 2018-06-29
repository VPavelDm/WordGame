package com.vpaveldm.wordgame.adapterLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.domainLayer.interactors.AddDeckInteractor;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author Pavel Vaitsikhouski
 */
public class AddDeckViewModel extends ViewModel {

    @Inject
    AddDeckInteractor mAddDeckInteractor;
    private final Deck mDeck;
    private MutableLiveData<LiveDataMessage> mMessageLiveData;
    private MutableLiveData<LiveDataMessage> mTranslateLiveData;

    public AddDeckViewModel() {
        mDeck = new Deck();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    /**
     * The method that subscribes to updates
     *
     * @param owner             object that is used to handle lifecycle changes
     * @param messageListener   callback object that is used for notifications about creating deck
     * @param translateListener callback object that is used for notifications about atu translating
     */
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

    /**
     * The method that adds a card to the deck
     *
     * @param card the object that is added to the deck cards
     */
    public void addCard(Card card) {
        mDeck.cards.add(card);
    }

    /**
     * The method that creates a deck with cards
     *
     * @param name name of deck
     * @return Disposable to manage the subscription
     */
    public Disposable createDeck(String name) {
        mDeck.deckName = name;
        return mAddDeckInteractor.addDeck(mDeck)
                .subscribe(
                        () -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
    }

    /**
     * The method that returns cards' amount of Deck
     *
     * @return Integer value equal to the cards' amount of Deck
     */
    public int getCardSize() {
        return mDeck.cards.size();
    }

    /**
     * The method that gets translate of word
     *
     * @param word the word to translate
     * @return Disposable to manage the subscription
     */
    public Disposable getAutoTranslate(String word) {
        return mAddDeckInteractor.getAutoTranslate(word).subscribe(
                success -> mTranslateLiveData.setValue(new LiveDataMessage(true, success)),
                e -> mTranslateLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
        );
    }
}
