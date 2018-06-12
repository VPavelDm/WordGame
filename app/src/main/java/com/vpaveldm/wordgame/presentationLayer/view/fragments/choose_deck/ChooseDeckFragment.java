package com.vpaveldm.wordgame.presentationLayer.view.fragments.choose_deck;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.databinding.FragmentPlayBinding;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.presentationLayer.viewModel.ChooseDeckViewModel;

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
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
        mChooseDeckViewModel = ViewModelProviders.of(this).get(ChooseDeckViewModel.class);
        mChooseDeckViewModel.subscribeOnDeckLiveData(this, decks -> adapter.swapList(decks));
        mChooseDeckViewModel.subscribeOnMessageLiveData(this, dataMessage -> {
            if (dataMessage == null) {
                return;
            }
            if (dataMessage.isSuccess()) {
                Toast.makeText(getContext(),
                        "Word is added",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(),
                        dataMessage.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPlayBinding binding = FragmentPlayBinding.inflate(inflater, container, false);
        ButterKnife.bind(this, binding.getRoot());
        binding.deckRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @OnClick(R.id.addDeckButton)
    void clickAddDeckButton() {
        mRouter.navigateTo(getString(R.string.fragment_add_deck));
    }
}
