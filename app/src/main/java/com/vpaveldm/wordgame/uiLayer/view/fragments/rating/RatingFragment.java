package com.vpaveldm.wordgame.uiLayer.view.fragments.rating;

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
import com.vpaveldm.wordgame.adapterLayer.viewModel.RatingViewModel;
import com.vpaveldm.wordgame.databinding.FragmentRatingBinding;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class RatingFragment extends Fragment {

    private static final String KEY_RESULT = "com.vpaveldm.result.game";
    @Inject
    Router mRouter;
    private static final String KEY_ID = "com.vpaveldm.id";
    private UserRecyclerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRatingBinding binding = FragmentRatingBinding.inflate(inflater, container, false);
        ButterKnife.bind(this, binding.getRoot());
        binding.topListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserRecyclerAdapter();
        binding.topListRV.setAdapter(adapter);
        Bundle args = Objects.requireNonNull(getArguments());
        binding.resultGameTV.setText(args.getString(KEY_RESULT));
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RatingViewModel ratingViewModel = ViewModelProviders.of(this).get(RatingViewModel.class);
        ratingViewModel.subscribe(this, topUserList -> {
            if (topUserList == null) {
                Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_LONG).show();
                mRouter.exit();
                return;
            }
            ratingViewModel.unsubscribe(this);
            adapter.swapUsers(topUserList.users);
        }, deck -> {
            if (deck == null) {
                return;
            }
            ratingViewModel.getUserTopList(deck);
        });
        Bundle args = Objects.requireNonNull(getArguments());
        String deckId = args.getString(KEY_ID);
        ratingViewModel.getDeck(deckId);
    }

    public static RatingFragment newInstance(String key, String resultGame) {

        Bundle args = new Bundle();
        args.putString(KEY_ID, key);
        args.putString(KEY_RESULT, resultGame);
        RatingFragment fragment = new RatingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.nextBtn)
    void clickNextBtn() {
        mRouter.backTo(getString(R.string.fragment_choose_deck));
    }
}
