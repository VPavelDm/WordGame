package com.vpaveldm.wordgame.presentationLayer.view.fragments.play;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.databinding.FragmentPlayingBinding;
import com.vpaveldm.wordgame.presentationLayer.viewModel.ChooseDeckViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayFragment extends Fragment {

    public static final String KEY_ID = "com.vpaveldm.id";
    private Deck mDeck;
    private int currentCard = 0;
    private FragmentPlayingBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getActivity() != null;
        ChooseDeckViewModel viewModel = ViewModelProviders.of(getActivity()).get(ChooseDeckViewModel.class);
        viewModel.subscribeOnDeckLiveData(this, decks -> {
            if (decks == null) {
                return;
            }
            Bundle args = getArguments();
            if (args == null) {
                return;
            }
            long id = args.getLong(KEY_ID);
            for (Deck deck : decks) {
                if (deck.getId() == id) {
                    mDeck = deck;
                    initWidgets(mDeck.getCards().get(currentCard));
                    return;
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlayingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public static PlayFragment newInstance(Long id) {

        Bundle args = new Bundle();
        args.putLong(KEY_ID, id);

        PlayFragment fragment = new PlayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initWidgets(Card card) {
        List<String> answers = Arrays.asList(new String[card.getWrongTranslates().size() + 1]);
        Collections.copy(answers, card.getWrongTranslates());
        answers.set(answers.size() - 1, card.getTranslate());
        Collections.shuffle(answers);
        binding.firstAnswerButton.setText(answers.get(0));
        binding.secondAnswerButton.setText(answers.get(1));
        binding.thirdAnswerButton.setText(answers.get(2));
        binding.fourthAnswerButton.setText(answers.get(3));
        binding.QuestionWordTV.setText(card.getWord());
    }
}