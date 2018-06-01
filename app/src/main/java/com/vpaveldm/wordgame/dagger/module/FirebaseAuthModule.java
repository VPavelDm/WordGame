package com.vpaveldm.wordgame.dagger.module;

import com.google.firebase.auth.FirebaseAuth;
import com.vpaveldm.wordgame.dagger.scope.FragmentScope;
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
