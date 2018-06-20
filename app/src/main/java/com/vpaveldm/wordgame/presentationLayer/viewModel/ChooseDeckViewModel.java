package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.domainLayer.interactors.ChooseDeckInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ChooseDeckViewModel extends ViewModel {

    @Inject
    ChooseDeckInteractor mChooseDeckInteractor;

    private MutableLiveData<List<Deck>> mDeckLiveData;

    public ChooseDeckViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribe(LifecycleOwner owner, Observer<List<Deck>> listener) {
        if (mDeckLiveData == null) {
            mDeckLiveData = new MutableLiveData<>();
        }
        mDeckLiveData.observe(owner, listener);
    }

    public void unsubscribe(LifecycleOwner owner) {
        mDeckLiveData.removeObservers(owner);
    }

    public Disposable getDecks() {
        return mChooseDeckInteractor.getDecks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(decks -> mDeckLiveData.setValue(decks));
    }
}
