package com.vpaveldm.wordgame.dataLayer.repository;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.dataLayer.model.LoggingModelInDataLayer;
import com.vpaveldm.wordgame.dataLayer.transform.DataLayerTransformer;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;

import java.net.ConnectException;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

@ActivityScope
public class LoggingRepositoryImpl implements ILoggingRepository {

    private DataLayerTransformer mTransformer;
    private FirebaseAuth mAuth;
    private GoogleApiClient mApiClient;

    @Inject
    LoggingRepositoryImpl(DataLayerTransformer transformer, FirebaseAuth auth, GoogleApiClient client) {
        mTransformer = transformer;
        mAuth = auth;
        mApiClient = client;
    }

    @Override
    public Single<Boolean> signIn(LoggingModelInDomainLayer model) {
        return Single.create(subscriber -> {
            LoggingModelInDataLayer dataModel = mTransformer.transform(model);
            if (dataModel.getEmail() != null && dataModel.getPassword() != null) {
                signInByEmailAndPassword(subscriber, dataModel);
            } else if (dataModel.getData() != null) {
                signInByGoogleIntent(subscriber, dataModel);
            }
        });
    }

    @Override
    public Single<Boolean> signUp(LoggingModelInDomainLayer model) {
        return Single.create(subscriber -> {
            LoggingModelInDataLayer dataModel = mTransformer.transform(model);
            String email = dataModel.getEmail();
            String password = dataModel.getPassword();
            if (email.equals("") || password.equals("")) {
                subscriber.onError(new IllegalArgumentException("Entry email or password field"));
                return;
            }
            Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);
            task.addOnCompleteListener(resultTask -> {
                if (resultTask.isSuccessful()) {
                    subscriber.onSuccess(true);
                } else {
                    Exception exception = resultTask.getException();
                    String message;
                    if (exception == null) {
                        message = "Unknown error";
                    } else {
                        message = exception.getMessage();
                    }
                    subscriber.onError(new IllegalArgumentException(message));
                }
            });
        });
    }

    @Override
    public LoggingModelInDataLayer getGoogleIntent() {
        LoggingModelInDataLayer.Builder builder = new LoggingModelInDataLayer.Builder();
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mApiClient);
        return builder.addData(intent).create();
    }

    private void signInByEmailAndPassword(SingleEmitter<Boolean> subscriber, LoggingModelInDataLayer model) {
        String email = model.getEmail();
        String password = model.getPassword();
        if (email.equals("") || password.equals("")) {
            subscriber.onError(new IllegalArgumentException("Entry email or password field"));
            return;
        }
        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password);
        task.addOnCompleteListener(authResultTask -> {
            if (authResultTask.isSuccessful()) {
                subscriber.onSuccess(true);
            } else {
                Exception exception = authResultTask.getException();
                String message;
                if (exception == null) {
                    message = "Unknown error";
                } else {
                    message = exception.getMessage();
                }
                subscriber.onError(new IllegalArgumentException(message));
            }
        });
    }

    private void signInByGoogleIntent(SingleEmitter<Boolean> subscriber, LoggingModelInDataLayer model) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(model.getData());
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            subscriber.onSuccess(true);
                        } else {
                            Exception exception = task1.getException();
                            if (exception == null) {
                                subscriber.onError(new ConnectException("Connection error"));
                            } else {
                                subscriber.onError(new ConnectException(exception.getMessage()));
                            }
                        }
                    });
        } catch (ApiException e) {
            subscriber.onError(new ConnectException(e.getMessage()));
        }
    }
}
