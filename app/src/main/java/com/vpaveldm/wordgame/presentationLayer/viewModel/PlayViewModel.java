package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.dataLayer.model.PlayModel;
import com.vpaveldm.wordgame.domainLayer.interactors.PlayInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import java.util.List;

import javax.inject.Inject;

public class PlayViewModel extends ViewModel {

    @Inject
    PlayInteractor mPlayInteractor;

    private MutableLiveData<List<PlayModel>> mDeckLiveData;

    public PlayViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribeOnDeckLiveData(LifecycleOwner owner, Observer<List<PlayModel>> listener) {
        if (mDeckLiveData == null) {
            mDeckLiveData = new MutableLiveData<>();
        }
        mDeckLiveData.observe(owner, listener);
    }

    @SuppressLint("CheckResult")
    public void getDecks() {
        mPlayInteractor.getDecks()
                .subscribe(decks -> mDeckLiveData.setValue(decks));
    }
}
