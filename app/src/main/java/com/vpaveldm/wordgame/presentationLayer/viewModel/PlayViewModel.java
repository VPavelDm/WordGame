package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.domainLayer.interactors.PlayInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.dataLayer.model.Deck;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class PlayViewModel extends ViewModel {

    @Inject
    PlayInteractor mPlayInteractor;

    private MutableLiveData<List<Deck>> mDeckLiveData;
    private MutableLiveData<LiveDataMessage> mMessageLiveData;

    public PlayViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribeOnDeckLiveData(LifecycleOwner owner, Observer<List<Deck>> listener) {
        if (mDeckLiveData == null) {
            mDeckLiveData = new MutableLiveData<>();
        }
        mDeckLiveData.observe(owner, listener);
    }

    public void subscribeOnMessageLiveData(LifecycleOwner owner, Observer<LiveDataMessage> listener) {
        if (mMessageLiveData == null) {
            mMessageLiveData = new MutableLiveData<>();
        }
        mMessageLiveData.observe(owner, listener);
    }

    //getDecks returns hot observable
    public Disposable getDecks() {
        return mPlayInteractor.getDecks()
                .subscribe(decks -> mDeckLiveData.setValue(decks));
    }
}
