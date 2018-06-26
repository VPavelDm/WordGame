package com.vpaveldm.wordgame.uiLayer.view.fragments.choose_deck;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.databinding.FragmentChooseDeckBinding;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.adapterLayer.viewModel.ChooseDeckViewModel;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

public class ChooseDeckFragment extends Fragment {

    @Inject
    Router mRouter;
    private ChooseDeckViewModel mChooseDeckViewModel;
    private DeckRecyclerAdapter adapter;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
        mChooseDeckViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ChooseDeckViewModel.class);
        mChooseDeckViewModel.subscribe(this, decks -> adapter.swapList(decks));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentChooseDeckBinding binding = FragmentChooseDeckBinding.inflate(inflater, container, false);
        ButterKnife.bind(this, binding.getRoot());
        binding.deckRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.deckRecyclerView.addItemDecoration(new ItemDivider(Objects.requireNonNull(getContext())));
        adapter = new DeckRecyclerAdapter();
        binding.deckRecyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Disposable disposable = mChooseDeckViewModel.getDecks();
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
    }

    @OnClick(R.id.addDeckButton)
    void clickAddDeckButton() {
        mRouter.navigateTo(getString(R.string.fragment_add_deck));
    }
}
