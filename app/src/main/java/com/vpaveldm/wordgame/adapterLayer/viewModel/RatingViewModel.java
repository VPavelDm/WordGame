package com.vpaveldm.wordgame.adapterLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.dataLayer.store.model.TopUserList;
import com.vpaveldm.wordgame.domainLayer.interactors.RatingInteractor;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class RatingViewModel extends ViewModel {

    private MutableLiveData<TopUserList> mUserListLiveData;
    @Inject
    RatingInteractor mInteractor;

    public RatingViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribe(LifecycleOwner owner, Observer<TopUserList> listener) {
        if (mUserListLiveData == null) {
            mUserListLiveData = new MutableLiveData<>();
        }
        mUserListLiveData.observe(owner, listener);
    }

    public void unsubscribe(LifecycleOwner owner) {
        mUserListLiveData.removeObservers(owner);
    }

    public Disposable getUserTopList(Deck deck) {
        return mInteractor.getTopUsers(deck)
                .subscribe(
                        topUserList -> mUserListLiveData.setValue(topUserList),
                        t -> mUserListLiveData.setValue(null),
                        () -> mUserListLiveData.setValue(null)
                );
    }

}
