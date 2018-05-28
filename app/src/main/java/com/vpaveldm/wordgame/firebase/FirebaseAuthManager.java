package com.vpaveldm.wordgame.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class FirebaseAuthManager {

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Inject
    public FirebaseAuthManager(FirebaseUser user, FirebaseAuth auth) {
        mUser = user;
        mAuth = auth;
    }

    public void signUp(String email, String password, final IFirebaseListener listener) {
        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listener.success();
                } else {
                    listener.failure();
                }
            }
        });
    }

    public void signIn(String email, String password, final IFirebaseListener listener) {
        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listener.success();
                } else {
                    listener.failure();
                }
            }
        });
    }

    public boolean isConnected() {
        return mUser != null;
    }
}
