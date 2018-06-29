package com.vpaveldm.wordgame.adapterLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.domainLayer.interactors.ChooseDeckInteractor;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author Pavel Vaitsikhouski
 */
public class ChooseDeckViewModel extends ViewModel {

    @Inject
    ChooseDeckInteractor mChooseDeckInteractor;

    private MutableLiveData<List<Deck>> mDeckLiveData;

    public ChooseDeckViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    /**
     * The method that subscribes to updates
     *
     * @param owner    object that is used to handle lifecycle changes
     * @param listener callback object that is used for notification about getting decks
     */
    public void subscribe(LifecycleOwner owner, Observer<List<Deck>> listener) {
        if (mDeckLiveData == null) {
            mDeckLiveData = new MutableLiveData<>();
        }
        mDeckLiveData.observe(owner, listener);
    }

    /**
     * The method that unsubscribes from updates
     *
     * @param owner object that is used to handle lifecycle changes
     */
    public void unsubscribe(LifecycleOwner owner) {
        mDeckLiveData.removeObservers(owner);
    }

    /**
     * The method that gets decks
     *
     * @return Disposable to manage the subscription
     */
    public Disposable getDecks() {
        return mChooseDeckInteractor.getDecks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(decks -> mDeckLiveData.setValue(decks));
    }
}
