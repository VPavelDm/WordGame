package com.vpaveldm.wordgame.presentationLayer.view.fragments.rating;

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
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.databinding.FragmentRatingBinding;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.presentationLayer.viewModel.ChooseDeckViewModel;
import com.vpaveldm.wordgame.presentationLayer.viewModel.RatingViewModel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;

public class RatingFragment extends Fragment {

    @Inject
    Router mRouter;
    private static final String KEY_ID = "com.vpaveldm.id";
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private UserRecyclerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RatingViewModel ratingViewModel = ViewModelProviders.of(this).get(RatingViewModel.class);
        ratingViewModel.subscribe(this, topUserList -> {
            if (topUserList == null) {
                return;
            }
            ratingViewModel.unsubscribe(this);
            adapter.swapUsers(topUserList.users);
        });

        assert getActivity() != null;
        ChooseDeckViewModel viewModel = ViewModelProviders.of(getActivity()).get(ChooseDeckViewModel.class);
        viewModel.subscribe(this, decks -> {
            if (decks == null) {
                return;
            }
            viewModel.unsubscribe(RatingFragment.this);
            Bundle args = getArguments();
            if (args == null) {
                return;
            }
            String id = args.getString(KEY_ID);
            for (Deck deck : decks) {
                if (deck.id.equals(id)) {
                    mCompositeDisposable.add(ratingViewModel.getUserTopList(deck));
                    return;
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRatingBinding binding = FragmentRatingBinding.inflate(inflater, container, false);
        ButterKnife.bind(this, binding.getRoot());
        binding.topListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserRecyclerAdapter();
        binding.topListRV.setAdapter(adapter);
        return binding.getRoot();
    }

    public static RatingFragment newInstance(String key) {

        Bundle args = new Bundle();
        args.putString(KEY_ID, key);
        RatingFragment fragment = new RatingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.nextBtn)
    void clickNextBtn() {
        mRouter.backTo(getString(R.string.fragment_choose_deck));
    }
}
