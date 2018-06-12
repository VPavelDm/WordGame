package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.domainLayer.interactors.ChooseDeckInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class ChooseDeckViewModel extends ViewModel {

    @Inject
    ChooseDeckInteractor mChooseDeckInteractor;

    private MutableLiveData<List<Deck>> mDeckLiveData;
    private MutableLiveData<LiveDataMessage> mMessageLiveData;

    public ChooseDeckViewModel() {
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
        return mChooseDeckInteractor.getDecks()
                .subscribe(decks -> mDeckLiveData.setValue(decks));
    }
}
