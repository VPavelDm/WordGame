package com.vpaveldm.wordgame.uiLayer.view.fragments.play;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.databinding.FragmentPlayingBinding;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.adapterLayer.viewModel.PlayViewModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

public class PlayFragment extends Fragment implements View.OnClickListener {

    @Inject
    Router mRouter;
    private static final String KEY_ID = "com.vpaveldm.id";

    private FragmentPlayingBinding binding;
    private PlayViewModel mPlayViewModel;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String deckId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlayingBinding.inflate(inflater, container, false);
        binding.firstAnswerButton.setOnClickListener(this);
        binding.secondAnswerButton.setOnClickListener(this);
        binding.thirdAnswerButton.setOnClickListener(this);
        binding.fourthAnswerButton.setOnClickListener(this);
        binding.noCorrectAnswerButton.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPlayViewModel = ViewModelProviders.of(this).get(PlayViewModel.class);

        mPlayViewModel.createLiveDataAndSubscribe(this,
                card -> initWidgets(Objects.requireNonNull(card)),
                message -> {
                    if (Objects.requireNonNull(message).isSuccess()) {
                        Pair<String, String> args;
                        if (message.getMessage() != null
                                && message.getMessage().equals(PlayViewModel.INCORRECT_ANSWER)) {
                            args = new Pair<>(deckId, "You lost the game!");
                        } else {
                            args = new Pair<>(deckId, "You won the game");
                        }
                        mRouter.replaceScreen(getString(R.string.fragment_rating), args);
                    } else {
                        Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_LONG).show();
                        mRouter.exit();
                    }
                });

        Bundle args = Objects.requireNonNull(getArguments());
        deckId = args.getString(KEY_ID);
        mPlayViewModel.getDeck(deckId);
    }

    @Override
    public void onStart() {
        super.onStart();
        mCompositeDisposable.add(mPlayViewModel.startGame());
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
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
        binding.noCorrectAnswerButton.setTag(answers.get(4));
        binding.QuestionWordTV.setText(card.word);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            Button button = (Button) v;
            Object tag = button.getTag();
            Disposable d;
            //If correct answer is noCorrectAnswer send tag
            if (tag != null) {
                d = mPlayViewModel.checkAnswer(String.valueOf(tag));
            } else {
                d = mPlayViewModel.checkAnswer(button.getText().toString());
            }
            if (d != null) {
                mCompositeDisposable.add(d);
            }
        }
    }
}