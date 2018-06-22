package com.vpaveldm.wordgame.dataLayer.repository;

import android.content.Intent;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.LoggingModel;

import java.net.ConnectException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

@ActivityScope
public class LoggingRepositoryImpl implements ILoggingRepository {

    private final FirebaseAuth mAuth;
    private final GoogleApiClient mApiClient;

    @Inject
    LoggingRepositoryImpl(FirebaseAuth auth, GoogleApiClient client) {
        mAuth = auth;
        mApiClient = client;
    }

    @Override
    public Observable<Boolean> signIn(LoggingModel model) {
        BehaviorSubject<Boolean> subject = BehaviorSubject.createDefault(false);
        if (model.email != null && model.password != null) {
            signInByEmailAndPassword(subject, model);
        } else if (model.data != null) {
            signInByGoogleIntent(subject, model);
        }
        return subject;
    }

    @Override
    public Observable<Boolean> signUp(LoggingModel model) {
        BehaviorSubject<Boolean> subject = BehaviorSubject.createDefault(false);
        signUpByEmailAndPassword(subject, model);
        return subject;
    }

    @Override
    public Single<LoggingModel> getGoogleIntent() {
        return Single.create(source -> {
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(mApiClient);
            source.onSuccess(new LoggingModel(intent));
        });
    }

    private void signInByEmailAndPassword(BehaviorSubject<Boolean> subject, LoggingModel model) {
        String email = model.email;
        String password = model.password;
        if (email.equals("") || password.equals("")) {
            subject.onError(new IllegalArgumentException("Entry email or password field"));
            return;
        }
        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password);
        task.addOnCompleteListener(authResultTask -> {
            if (authResultTask.isSuccessful()) {
                subject.onNext(true);
                subject.onComplete();
            } else {
                Exception exception = authResultTask.getException();
                String message;
                if (exception == null) {
                    message = "Unknown error";
                } else {
                    message = exception.getMessage();
                }
                subject.onError(new IllegalArgumentException(message));
            }
        });
    }

    private void signInByGoogleIntent(BehaviorSubject<Boolean> subject, LoggingModel model) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(model.data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            subject.onNext(true);
                            subject.onComplete();
                        } else {
                            Exception exception = task1.getException();
                            if (exception == null) {
                                subject.onError(new ConnectException("Connection error"));
                            } else {
                                subject.onError(new ConnectException(exception.getMessage()));
                            }
                        }
                    });
        } catch (ApiException e) {
            subject.onError(new ConnectException(e.getMessage()));
        }
    }

    private void signUpByEmailAndPassword(BehaviorSubject<Boolean> subject, LoggingModel model) {
        String email = model.email;
        String password = model.password;
        if (email.equals("") || password.equals("")) {
            subject.onError(new IllegalArgumentException("Entry email or password field"));
            return;
        }
        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);
        task.addOnCompleteListener(resultTask -> {
            if (resultTask.isSuccessful()) {
                subject.onNext(true);
                subject.onComplete();
            } else {
                Exception exception = resultTask.getException();
                String message;
                if (exception == null) {
                    message = "Unknown error";
                } else {
                    message = exception.getMessage();
                }
                subject.onError(new IllegalArgumentException(message));
            }
        });
    }
}
