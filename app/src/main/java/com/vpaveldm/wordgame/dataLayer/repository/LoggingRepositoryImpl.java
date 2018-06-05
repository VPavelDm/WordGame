package com.vpaveldm.wordgame.dataLayer.repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.errors.IErrorListener;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.dataLayer.model.LoggingModelInDataLayer;
import com.vpaveldm.wordgame.dataLayer.transform.DataLayerTransformer;
import com.vpaveldm.wordgame.domainLayer.model.LoggingModelInDomainLayer;

import javax.inject.Inject;

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
    public void signIn(final LoggingModelInDomainLayer model, final IErrorListener listener) {
        LoggingModelInDataLayer dataModel = mTransformer.transform(model);
        String email = dataModel.getEmail();
        String password = dataModel.getPassword();
        if (email.equals("") || password.equals("")){
            listener.failure("Entry email or password field");
            return;
        }

        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listener.success();
                } else {
                    Exception exception = task.getException();
                    String message;
                    if (exception == null) {
                        message = "Unknown error";
                    } else {
                        message = exception.getMessage();
                    }
                    listener.failure(message);
                }
            }
        });
    }

    @Override
    public void signUp(LoggingModelInDomainLayer model, final IErrorListener listener) {
        LoggingModelInDataLayer dataModel = mTransformer.transform(model);
        String email = dataModel.getEmail();
        String password = dataModel.getPassword();
        if (email.equals("") || password.equals("")){
            listener.failure("Entry email or password field");
            return;
        }

        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listener.success();
                } else {
                    Exception exception = task.getException();
                    String message;
                    if (exception == null) {
                        message = "Unknown error";
                    } else {
                        message = exception.getMessage();
                    }
                    listener.failure(message);
                }
            }
        });
    }
}
