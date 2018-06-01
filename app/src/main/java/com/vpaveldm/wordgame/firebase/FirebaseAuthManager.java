package com.vpaveldm.wordgame.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vpaveldm.wordgame.R;

import javax.inject.Inject;

public class FirebaseAuthManager {

    private FirebaseAuth mAuth;

    @Inject
    public FirebaseAuthManager(FirebaseAuth auth) {
        mAuth = auth;
    }

    public void signUp(String email, String password, final IFirebaseListener listener) {
        if (isEmptyEmailOrPassword(email, password)) {
            listener.failure(R.string.error_incorrect_email_or_password);
            return;
        }
        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (listener == null)
                    return;
                if (task.isSuccessful()) {
                    listener.success();
                } else {
                    listener.failure(R.string.error_registration_denied);
                }
            }
        });
    }

    public void signIn(String email, String password, final IFirebaseListener listener) {
        if (isEmptyEmailOrPassword(email, password)) {
            listener.failure(R.string.error_incorrect_email_or_password);
            return;
        }
        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (listener == null)
                    return;
                if (task.isSuccessful()) {
                    listener.success();
                } else {
                    listener.failure(R.string.error_no_user_found);
                }
            }
        });
    }

    public void signIn(Task<GoogleSignInAccount> task, final IFirebaseListener listener) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                listener.success();
                            } else {
                                listener.failure(R.string.error_no_user_found);
                            }
                        }
                    });
        } catch (ApiException e) {
            listener.failure(R.string.error_google_connection);
        }
    }

    public void signOut() {
        mAuth.signOut();
    }

    public boolean isConnected() {
        return mAuth.getCurrentUser() != null;
    }

    private boolean isEmptyEmailOrPassword(String email, String password) {
        return email.equals("") || password.equals("");
    }
}
