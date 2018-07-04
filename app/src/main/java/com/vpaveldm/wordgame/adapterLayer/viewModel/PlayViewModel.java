package com.vpaveldm.wordgame.adapterLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.vpaveldm.wordgame.adapterLayer.dataBindingModel.Question;
import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.domainLayer.interactors.PlayInteractor;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Pavel Vaitsikhouski
 */
public class PlayViewModel extends ViewModel {
    @Inject
    PlayInteractor mInteractor;
    public static final String INCORRECT_ANSWER = "Incorrect answer";
    public final ObservableField<Question> question = new ObservableField<>();
    public final ObservableBoolean visible = new ObservableBoolean();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Deck mDeck;
    private int currentCard = 0;
    private MutableLiveData<LiveDataMessage> mMessageLiveData;
    private long time = 0L;

    /**
     * Constructs view model
     */
    public PlayViewModel() {
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    /**
     * The method that creates live date and subscribes to updates
     *
     * @param owner           object that is used to handle lifecycle changes
     * @param messageObserver callback object that is used for notifications when the game ends
     */
    public void createLiveDataAndSubscribe(LifecycleOwner owner,
                                           Observer<LiveDataMessage> messageObserver) {
        mMessageLiveData = new MutableLiveData<>();
        mMessageLiveData.observe(owner, messageObserver);
    }

    /**
     * Start stopwatch and get deck with cards
     *
     * @param id Deck's id to play
     */
    public void startGame(String id) {
        Disposable d = mInteractor.startGame().subscribe(t -> ++time);
        mCompositeDisposable.add(d);

        d = mInteractor.getDeck(id)
                .subscribe(deck -> {
                    mDeck = deck;
                    initWidgets(mDeck.cards.get(currentCard));
                });
        mCompositeDisposable.add(d);
    }

    /**
     * Processing a button press to check the answer
     *
     * @param answer User's answer to check
     */
    public void clickAnswerBtn(String answer) {
        Card card = mDeck.cards.get(currentCard);
        if (card.translate.equals(answer)) { //If answer is correct turn next card
            ++currentCard;
            if (currentCard != mDeck.cards.size()) {
                initWidgets(mDeck.cards.get(currentCard));
            } else { //If there isn't any cards
                Disposable d = mInteractor.updateTopList(mDeck, time)
                        .doOnSubscribe(s -> visible.set(true))
                        .doOnEvent(t -> visible.set(false))
                        .subscribe(
                                () -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                                t -> mMessageLiveData.setValue(new LiveDataMessage(true, null))
                        );
                mCompositeDisposable.add(d);
            }
        } else { //If answer is incorrect
            mMessageLiveData.setValue(new LiveDataMessage(true, INCORRECT_ANSWER));
        }
    }

    private void initWidgets(Card card) {
        List<String> answers = Arrays.asList(new String[card.wrongTranslates.size() + 1]);
        Collections.copy(answers, card.wrongTranslates);
        answers.set(answers.size() - 1, card.translate);
        Collections.shuffle(answers);
        question.set(new Question(card.word, answers));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
