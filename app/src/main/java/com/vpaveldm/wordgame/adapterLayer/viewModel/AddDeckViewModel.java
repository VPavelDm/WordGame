package com.vpaveldm.wordgame.adapterLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.domainLayer.interactors.AddDeckInteractor;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Pavel Vaitsikhouski
 */
public class AddDeckViewModel extends ViewModel {

    private static final int MINIMUM_CARDS = 10;
    private static final int COUNT_OF_WRONG_TRANSLATES = 4;

    @Inject
    AddDeckInteractor mAddDeckInteractor;

    public final ObservableBoolean visible = new ObservableBoolean();
    public final ObservableInt cardsCount = new ObservableInt();
    public final ObservableInt wrongWordsCount = new ObservableInt();
    public final ObservableField<String> wordET = new ObservableField<>();
    public final ObservableField<String> translateET = new ObservableField<>();
    public final ObservableField<String> wrongTranslateET = new ObservableField<>();

    private final Deck mDeck;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<LiveDataMessage> mMessageLiveData;

    public AddDeckViewModel() {
        ActivityComponentManager.getActivityComponent().inject(this);

        //Assign cardsCount to -1 'cause each time when resetObservableFields is called,
        // cardsCount is incremented
        cardsCount.set(-1);
        resetObservableFields();

        mDeck = new Deck();
        mDeck.cards.add(new Card());
    }

    /**
     * The method that subscribes to updates
     *
     * @param owner           object that is used to handle lifecycle changes
     * @param messageListener callback object that is used for notifications about creating deck
     */
    public void subscribe(LifecycleOwner owner,
                          Observer<LiveDataMessage> messageListener) {
        mMessageLiveData = new MutableLiveData<>();
        mMessageLiveData.observe(owner, messageListener);
    }

    /**
     * Processing a button press to add wrong translate
     */
    public void clickAddWrongTranslate() {
        //Get last card's wrong translates
        List<String> wrongTranslates = mDeck.cards.get(mDeck.cards.size() - 1).wrongTranslates;
        if (wrongTranslates.size() == COUNT_OF_WRONG_TRANSLATES) {
            mMessageLiveData.setValue(new LiveDataMessage(false, "All wrong words are added"));
            return;
        }
        String wrongTranslate = Objects.requireNonNull(wrongTranslateET.get());
        if (wrongTranslate.equals("")) {
            mMessageLiveData.setValue(new LiveDataMessage(false, "Entry text in wrong translate field"));
            return;
        }
        wrongTranslates.add(wrongTranslate);
        wrongTranslateET.set("");
        wrongWordsCount.set(wrongTranslates.size());
    }

    /**
     * Processing a button press to get word's translate
     *
     * @param word Word to translate
     */
    public void clickAutoTranslate(String word) {
        if (word.equals("")) {
            mMessageLiveData.setValue(new LiveDataMessage(false, "Entry text in word field"));
            return;
        }
        Disposable d = mAddDeckInteractor.getAutoTranslate(word)
                .doOnEvent((s, t) -> visible.set(false))
                .doOnSubscribe(s -> visible.set(true))
                .subscribe(
                        translateET::set,
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
        mCompositeDisposable.add(d);
    }

    /**
     * Processing a button press to create card
     */
    public void clickCreateCard() {
        if (Objects.equals(wordET.get(), "")) {
            mMessageLiveData.setValue(new LiveDataMessage(false, "Entry word"));
            return;
        }
        if (Objects.equals(translateET.get(), "")) {
            mMessageLiveData.setValue(new LiveDataMessage(false, "Entry translate"));
            return;
        }
        Card card = mDeck.cards.get(mDeck.cards.size() - 1);
        if (card.wrongTranslates.size() != COUNT_OF_WRONG_TRANSLATES) {
            mMessageLiveData.setValue(new LiveDataMessage(false,
                    "Entry " + COUNT_OF_WRONG_TRANSLATES + " wrong translates"));
            return;
        }
        card.word = wordET.get();
        card.translate = translateET.get();
        resetObservableFields();
        //Add empty card
        mDeck.cards.add(new Card());
    }

    /**
     * Processing a button press to create deck
     *
     * @param deckName Deck's name
     */
    public void clickCreateDeck(String deckName) {
        if (deckName.equals("")) {
            mMessageLiveData.setValue(new LiveDataMessage(false, "Entry deck name"));
            return;
        }
        if (mDeck.cards.size() < MINIMUM_CARDS) {
            mMessageLiveData.setValue(new LiveDataMessage(false,
                    "Add at least " + MINIMUM_CARDS + " words"));
            return;
        }
        mDeck.deckName = deckName;
        //Remove last empty card
        mDeck.cards.remove(mDeck.cards.size() - 1);
        Disposable d = mAddDeckInteractor.addDeck(mDeck)
                .subscribe(
                        () -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
        mCompositeDisposable.add(d);
    }

    private void resetObservableFields() {
        wordET.set("");
        translateET.set("");
        wrongTranslateET.set("");
        wrongWordsCount.set(0);
        cardsCount.set(cardsCount.get() + 1);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
