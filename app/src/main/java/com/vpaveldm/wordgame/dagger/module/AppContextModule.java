package com.vpaveldm.wordgame.dagger.module;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppContextModule {

    private final Context mContext;

    public AppContextModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    @Named("Application")
    public Context provideAppContext() {
        return mContext;
    }
}
