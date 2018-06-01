package com.vpaveldm.wordgame.fragments.logging;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.api.GoogleApiClient;
import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.firebase.FirebaseAuthManager;
import com.vpaveldm.wordgame.firebase.IFirebaseListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

/**
 * Class representing the logging UI
 *
 * @author Pavel Vaitsikhouski
 */
public class LoggingFragment extends Fragment implements IFirebaseListener {

    @Inject
    FirebaseAuthManager mAuthManager;
    @Inject
    Router mRouter;
    @Inject
    GoogleApiClient mGoogleSignInClient;
    @BindView(R.id.emailET)
    EditText emailET;
    @BindView(R.id.passwordET)
    EditText passwordET;
    private static final int RC_SIGN_IN = 1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLifecycle().addObserver(new LoggingComponentManager(this));
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
        mAuthManager.signUp(email, password, this);
    }

    @OnClick(R.id.loginButton)
    void clickLoginButton() {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        mAuthManager.signIn(email, password, this);
    }

    @OnClick(R.id.googleLoginButton)
    void clickGoogleLoginButton() {
        if (mGoogleSignInClient == null) {
            failure(R.string.error_google_connection);
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_SIGN_IN: {
                mAuthManager.signIn(GoogleSignIn.getSignedInAccountFromIntent(data), this);
                break;
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void success() {
        mRouter.replaceScreen(getString(R.string.fragment_menu));
    }

    @Override
    public void failure(int msgId) {
        Toast.makeText(
                getContext(),
                getString(msgId),
                Toast.LENGTH_LONG
        ).show();
    }
}
