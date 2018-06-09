package com.vpaveldm.wordgame.presentationLayer.view.fragments.play;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.databinding.FragmentPlayBinding;
import com.vpaveldm.wordgame.presentationLayer.viewModel.LiveDataMessage;
import com.vpaveldm.wordgame.presentationLayer.viewModel.PlayViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PlayFragment extends Fragment {

    private PlayViewModel mPlayViewModel;
    private DeckAdapter adapter;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPlayViewModel = ViewModelProviders.of(this).get(PlayViewModel.class);
        mPlayViewModel.subscribeOnDeckLiveData(this, decks -> adapter.swapList(decks));
        mPlayViewModel.subscribeOnMessageLiveData(this, dataMessage -> {
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
        adapter = new DeckAdapter();
        binding.deckRecyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Disposable disposable = mPlayViewModel.getDecks();
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @OnClick(R.id.addDeckButton)
    void clickAddDeckButton() {
        AddDeckDialog addDeckDialog = new AddDeckDialog();
        assert getFragmentManager() != null;
        addDeckDialog.show(getFragmentManager(), null);
    }
}
