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
import io.reactivex.disposables.Disposable;

/**
 * @author Pavel Vaitsikhouski
 */
public class RatingViewModel extends ViewModel {

    private MutableLiveData<TopUserList> mUserListLiveData;
    private MutableLiveData<Deck> mDeckLiveData;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    @Inject
    RatingInteractor mInteractor;

    /**
     * Constructs view model object
     */
    public RatingViewModel() {
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    /**
     * Create live data and subscribe on it
     *
     * @param owner        object that is used to handle lifecycle changes
     * @param listener     callback object that is used for notifications when top user list is received
     * @param deckListener callback object that is used for notifications when deck is received
     */
    public void subscribe(LifecycleOwner owner, Observer<TopUserList> listener, Observer<Deck> deckListener) {
        if (mUserListLiveData == null || mDeckLiveData == null) {
            mUserListLiveData = new MutableLiveData<>();
            mDeckLiveData = new MutableLiveData<>();
        }
        mUserListLiveData.observe(owner, listener);
        mDeckLiveData.observe(owner, deckListener);
    }

    /**
     * Send a request to the server to receive top user list
     *
     * @param deck Deck for which the top user list is requested
     */
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

    /**
     * Send a request to the server to receive deck by id
     *
     * @param id Id to get
     */
    public void getDeck(String id) {
        Disposable d = mInteractor.getDeck(id).subscribe(deck -> mDeckLiveData.setValue(deck));
        mCompositeDisposable.add(d);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
