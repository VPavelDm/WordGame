package com.vpaveldm.wordgame.uiLayer.view.fragments.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.databinding.FragmentMenuBinding;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class MenuFragment extends Fragment {
    @Inject
    FirebaseAuth mAuth;
    @Inject
    Router mRouter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMenuBinding binding = FragmentMenuBinding.inflate(inflater, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    public void clickPlay() {
        mRouter.navigateTo(getString(R.string.fragment_choose_deck));
    }

    public void clickLogOut() {
        mAuth.signOut();
        mRouter.replaceScreen(getString(R.string.fragment_login));
    }
}
