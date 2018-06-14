package com.vpaveldm.wordgame.presentationLayer.view.fragments.play;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.databinding.FragmentPlayingBinding;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.presentationLayer.viewModel.ChooseDeckViewModel;
import com.vpaveldm.wordgame.presentationLayer.viewModel.PlayViewModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class PlayFragment extends Fragment implements View.OnClickListener {

    @Inject
    Router mRouter;
    public static final String KEY_ID = "com.vpaveldm.id";

    private FragmentPlayingBinding binding;
    private PlayViewModel mPlayViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPlayViewModel = ViewModelProviders.of(this).get(PlayViewModel.class);
        mPlayViewModel.subscribe(this, card -> {
            if (card == null) {
                return;
            }
            initWidgets(card);
        }, message -> {
            if (message == null) {
                return;
            }
            if (!message.isSuccess()) {
                Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_LONG).show();
                //#Добавить здесь диалоговое окно, которое выведет результат
                mRouter.exit();
            } else {
                Toast.makeText(getContext(), "You win!!!", Toast.LENGTH_LONG).show();
                mRouter.exit();
            }
        });

        assert getActivity() != null;
        ChooseDeckViewModel viewModel = ViewModelProviders.of(getActivity()).get(ChooseDeckViewModel.class);
        Observer<List<Deck>> listener = new Observer<List<Deck>>() {
            @Override
            public void onChanged(@Nullable List<Deck> decks) {
                if (decks == null) {
                    return;
                }
                viewModel.unsubscribe(this);
                Bundle args = getArguments();
                if (args == null) {
                    return;
                }
                String id = args.getString(KEY_ID);
                for (Deck deck : decks) {
                    if (deck.id.equals(id)) {
                        mPlayViewModel.startGame(deck);
                        return;
                    }
                }
            }
        };
        viewModel.subscribe(this, listener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlayingBinding.inflate(inflater, container, false);
        binding.firstAnswerButton.setOnClickListener(this);
        binding.secondAnswerButton.setOnClickListener(this);
        binding.thirdAnswerButton.setOnClickListener(this);
        binding.fourthAnswerButton.setOnClickListener(this);
        return binding.getRoot();
    }

    public static PlayFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(KEY_ID, id);

        PlayFragment fragment = new PlayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initWidgets(Card card) {
        List<String> answers = Arrays.asList(new String[card.wrongTranslates.size() + 1]);
        Collections.copy(answers, card.wrongTranslates);
        answers.set(answers.size() - 1, card.translate);
        Collections.shuffle(answers);
        binding.firstAnswerButton.setText(answers.get(0));
        binding.secondAnswerButton.setText(answers.get(1));
        binding.thirdAnswerButton.setText(answers.get(2));
        binding.fourthAnswerButton.setText(answers.get(3));
        binding.QuestionWordTV.setText(card.word);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            Button button = (Button) v;
            mPlayViewModel.checkAnswer(button.getText().toString());
        }
    }
}