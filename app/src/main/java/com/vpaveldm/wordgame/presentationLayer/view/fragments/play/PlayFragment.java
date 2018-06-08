package com.vpaveldm.wordgame.presentationLayer.view.fragments.play;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpaveldm.wordgame.databinding.FragmentPlayBinding;
import com.vpaveldm.wordgame.presentationLayer.viewModel.PlayViewModel;

public class PlayFragment extends Fragment {

    private PlayViewModel mPlayViewModel;
    private DeckAdapter adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPlayViewModel = ViewModelProviders.of(this).get(PlayViewModel.class);
        mPlayViewModel.subscribeOnDeckLiveData(this, decks -> adapter.swapList(decks));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPlayBinding binding = FragmentPlayBinding.inflate(inflater, container, false);
        binding.deckRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new DeckAdapter();
        binding.deckRecyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPlayViewModel.getDecks();
    }
}
