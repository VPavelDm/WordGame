package com.vpaveldm.wordgame.adapterLayer.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Pair;

import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.domainLayer.interactors.ChooseDeckInteractor;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Pavel Vaitsikhouski
 */
public class ChooseDeckViewModel extends ViewModel {

    @Inject
    ChooseDeckInteractor mChooseDeckInteractor;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    public final LiveData<PagedList<Deck>> decksList;

    public ChooseDeckViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
        Pair<Completable, DataSource.Factory<Integer, Deck>> pair = mChooseDeckInteractor.getDeckDataSource();
        decksList = new LivePagedListBuilder<>(
                pair.second, 20
        ).build();
        mCompositeDisposable.add(pair.first.subscribe());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
