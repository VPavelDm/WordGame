package com.vpaveldm.wordgame.dataLayer.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.dataLayer.model.LoggingModelInDataLayer;
import com.vpaveldm.wordgame.dataLayer.transform.DataLayerTransformer;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;

import javax.inject.Inject;

import io.reactivex.Single;

@ActivityScope
public class LoggingRepositoryImpl implements ILoggingRepository {

    private DataLayerTransformer mTransformer;
    private FirebaseAuth mAuth;

    @Inject
    LoggingRepositoryImpl(DataLayerTransformer transformer, FirebaseAuth auth) {
        mTransformer = transformer;
        mAuth = auth;
    }

    @Override
    public Single<Boolean> signIn(LoggingModelInDomainLayer model) {
        return Single.create(subscriber -> {
            LoggingModelInDataLayer dataModel = mTransformer.transform(model);
            String email = dataModel.getEmail();
            String password = dataModel.getPassword();
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
}
