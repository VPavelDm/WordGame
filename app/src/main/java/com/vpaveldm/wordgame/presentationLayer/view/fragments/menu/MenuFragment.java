package com.vpaveldm.wordgame.presentationLayer.view.fragments.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.playButton)
    void clickPlayButton() {
        mRouter.navigateTo(getString(R.string.fragment_play));
    }

    @OnClick(R.id.logOutButton)
    void clickLogOutButton() {
        mAuth.signOut();
        mRouter.replaceScreen(getString(R.string.fragment_login));
    }
}
