package com.vpaveldm.wordgame.dagger.module;

import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vpaveldm.wordgame.firebase.FirebaseAuthManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseAuthModule {

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    public FirebaseAuthManager provideFirebaseAuthManager(FirebaseAuth auth) {
        return new FirebaseAuthManager(auth);
    }

}
