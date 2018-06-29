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
import android.widget.Toast;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.adapterLayer.viewModel.ChooseDeckViewModel;
import com.vpaveldm.wordgame.databinding.FragmentChooseDeckBinding;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import java.util.Objects;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

/**
 * @author Pavel Vaitsikhouski
 */
public class ChooseDeckFragment extends Fragment {

    @Inject
    Router mRouter;
    private ChooseDeckViewModel mChooseDeckViewModel;
    private DeckAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChooseDeckViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ChooseDeckViewModel.class);
        mChooseDeckViewModel.subscribe(this, liveDataMessage -> {
            if (!Objects.requireNonNull(liveDataMessage).isSuccess()){
                Toast.makeText(getContext(), liveDataMessage.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentChooseDeckBinding binding = FragmentChooseDeckBinding.inflate(inflater, container, false);
        binding.setHandler(this);
        binding.setViewmodel(mChooseDeckViewModel);

        binding.deckRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.deckRecyclerView.addItemDecoration(new ItemDivider(Objects.requireNonNull(getContext())));
        adapter = new DeckAdapter();
        mChooseDeckViewModel.decksList.observe(this, pagedList -> adapter.submitList(pagedList));
        binding.deckRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    /**
     * Processing a button press to change Fragment to AddDeckFragment
     */
    public void clickAddDeckButton() {
        mRouter.navigateTo(getString(R.string.fragment_add_deck));
    }
}
