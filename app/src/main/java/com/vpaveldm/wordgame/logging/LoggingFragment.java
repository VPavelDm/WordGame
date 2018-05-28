package com.vpaveldm.wordgame.logging;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.vpaveldm.wordgame.Application;
import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.firebase.FirebaseAuthManager;
import com.vpaveldm.wordgame.firebase.IFirebaseListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Class representing the logging UI
 *
 * @author Pavel Vaitsikhouski
 */
public class LoggingFragment extends Fragment {

    @Inject
    FirebaseAuthManager mAuthManager;
    @BindView(R.id.emailET)
    EditText emailET;
    @BindView(R.id.passwordET)
    EditText passwordET;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logging, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.registerButton)
    void clickRegisterButton() {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        mAuthManager.signUp(email, password, new IFirebaseListener() {
            @Override
            public void success() {
                Log.i("signupTAG", "success");
            }

            @Override
            public void failure() {
                Log.i("signupTAG", "failure");
            }
        });
    }

    @OnClick(R.id.loginButton)
    void clickLoginButton() {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        mAuthManager.signIn(email, password, new IFirebaseListener() {
            @Override
            public void success() {

            }

            @Override
            public void failure() {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity == null)
                    return;
                DialogFragment noUserDialogFragment = new NoUserDialog();
                noUserDialogFragment.show(activity.getSupportFragmentManager(), null);
            }
        });
    }
}
