package com.vpaveldm.wordgame.adapterLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.domainLayer.interactors.ChooseDeckInteractor;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Pavel Vaitsikhouski
 */
public class ChooseDeckViewModel extends ViewModel {

    @Inject
    ChooseDeckInteractor mChooseDeckInteractor;

    public ObservableBoolean visible = new ObservableBoolean();
    public final LiveData<PagedList<Deck>> decksList;
    private MutableLiveData<LiveDataMessage> mMessageLiveData;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public ChooseDeckViewModel() {
        ActivityComponentManager.getActivityComponent().inject(this);
        DataSource.Factory<Integer, Deck> dataSource = mChooseDeckInteractor.getDeckDataSource();
        decksList = new LivePagedListBuilder<>(dataSource, 20).build();
    }

    /**
     * The method that subscribes to updates
     *
     * @param owner    object that is used to handle lifecycle changes
     * @param listener callback object that is used for notifications when error is received
     */
    public void subscribe(LifecycleOwner owner, Observer<LiveDataMessage> listener) {
        mMessageLiveData = new MutableLiveData<>();
        mMessageLiveData.observe(owner, listener);
        Disposable d = mChooseDeckInteractor.subscribeOnUpdate()
                .doOnEvent(o -> visible.set(false))
                .doOnSubscribe(s -> visible.set(true))
                .subscribe(
                        () -> {
                        },
                        t -> mMessageLiveData.setValue(new LiveDataMessage(false, t.getMessage()))
                );
        mCompositeDisposable.add(d);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
