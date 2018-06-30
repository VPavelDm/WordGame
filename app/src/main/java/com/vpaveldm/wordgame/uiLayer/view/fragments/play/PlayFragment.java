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
import android.widget.Toast;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.adapterLayer.viewModel.PlayViewModel;
import com.vpaveldm.wordgame.databinding.FragmentPlayingBinding;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import java.util.Objects;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class PlayFragment extends Fragment {

    @Inject
    Router mRouter;

    private static final String KEY_ID = "com.vpaveldm.id";
    private PlayViewModel mPlayViewModel;
    private String deckId;
    private FragmentPlayingBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentPlayingBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPlayViewModel = ViewModelProviders.of(this).get(PlayViewModel.class);
        mBinding.setHandler(mPlayViewModel);

        mPlayViewModel.createLiveDataAndSubscribe(this,
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
        mPlayViewModel.startGame(deckId);
    }

    public static PlayFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(KEY_ID, id);

        PlayFragment fragment = new PlayFragment();
        fragment.setArguments(args);
        return fragment;
    }
}