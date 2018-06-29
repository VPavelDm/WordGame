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

import io.reactivex.disposables.CompositeDisposable;

public class RatingViewModel extends ViewModel {

    private MutableLiveData<TopUserList> mUserListLiveData;
    private MutableLiveData<Deck> mDeckLiveData;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    @Inject
    RatingInteractor mInteractor;

    public RatingViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribe(LifecycleOwner owner, Observer<TopUserList> listener, Observer<Deck> deckListener) {
        if (mUserListLiveData == null || mDeckLiveData == null) {
            mUserListLiveData = new MutableLiveData<>();
            mDeckLiveData = new MutableLiveData<>();
        }
        mUserListLiveData.observe(owner, listener);
        mDeckLiveData.observe(owner, deckListener);
    }

    public void unsubscribe(LifecycleOwner owner) {
        mUserListLiveData.removeObservers(owner);
    }

    public void getUserTopList(Deck deck) {
        mCompositeDisposable.add(
                mInteractor.getTopUsers(deck)
                        .doOnDispose(() -> mCompositeDisposable.clear())
                        .subscribe(
                                topUserList -> mUserListLiveData.setValue(topUserList),
                                t -> mUserListLiveData.setValue(null),
                                () -> mUserListLiveData.setValue(null)
                        )
        );
    }

    public void getDeck(String id) {
        mCompositeDisposable.add(mInteractor.getDeck(id)
                .subscribe(deck -> mDeckLiveData.setValue(deck)));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
