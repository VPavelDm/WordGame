package com.vpaveldm.wordgame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.firebase.FirebaseAuthManager;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class MainActivity extends AppCompatActivity {

    @Inject
    Router mRouter;
    @Inject
    FirebaseAuthManager mFirebaseAuthManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new DaggerActivityInitializer(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFirebaseAuthManager.isConnected()){

        } else {
            mRouter.navigateTo(getString(R.string.fragment_login));
        }
    }
}
