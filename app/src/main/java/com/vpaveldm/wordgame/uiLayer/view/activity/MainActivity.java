package com.vpaveldm.wordgame.uiLayer.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.vpaveldm.wordgame.R;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class MainActivity extends AppCompatActivity {

    @Inject
    Router mRouter;
    @Inject
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new ActivityComponentManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentById(R.id.fragmentContainer) == null) {
            if (mAuth.getCurrentUser() != null) {
                mRouter.replaceScreen(getString(R.string.fragment_menu));
            } else {
                mRouter.replaceScreen(getString(R.string.fragment_login));
            }
        }
    }
}
