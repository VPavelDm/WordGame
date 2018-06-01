package com.vpaveldm.wordgame.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class FirebaseAuthManager {

    private FirebaseAuth mAuth;

    @Inject
    public FirebaseAuthManager(FirebaseAuth auth) {
        mAuth = auth;
    }

    public void signUp(String email, String password, final IFirebaseListener listener) {
        if (isEmptyEmailOrPassword(email, password)) {
            listener.failure();
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
                    listener.failure();
                }
            }
        });
    }

    public void signIn(String email, String password, final IFirebaseListener listener) {
        if (isEmptyEmailOrPassword(email, password)) {
            listener.failure();
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
                    listener.failure();
                }
            }
        });
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
