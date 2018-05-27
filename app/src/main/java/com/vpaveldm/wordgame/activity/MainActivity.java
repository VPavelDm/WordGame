package com.vpaveldm.wordgame.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.activity.InitializerActivity;
import com.vpaveldm.wordgame.logging.LoggingFragment;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private final static String MAIN_ACTIVITY_TAG = "mainActivityTAG";

    @Inject
    FirebaseAuth mAuth;
    @Inject
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new InitializerActivity(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            Log.i(MAIN_ACTIVITY_TAG, "login");
            if (mFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
                Fragment fragment = new LoggingFragment();
                mFragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer, fragment)
                        .commit();
            }
        }
    }
}
