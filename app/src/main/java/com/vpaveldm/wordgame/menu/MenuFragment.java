package com.vpaveldm.wordgame.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.firebase.FirebaseAuthManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class MenuFragment extends Fragment {

    @Inject
    FirebaseAuthManager mAuthManager;
    @Inject
    Router mRouter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }

    @OnClick(R.id.outButton)
    void clickOutButton() {
        mAuthManager.signOut();
        mRouter.newRootScreen(getString(R.string.fragment_login));
    }
}
