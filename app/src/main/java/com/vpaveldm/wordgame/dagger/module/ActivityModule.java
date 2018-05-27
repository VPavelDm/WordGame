package com.vpaveldm.wordgame.dagger.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(Context context) {
        if (!(context instanceof AppCompatActivity))
            throw new IllegalArgumentException("It is expected AppCompatActivity");
        mActivity = (AppCompatActivity) context;
    }

    @Provides
    @ActivityScope
    public FragmentManager provideFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Provides
    @ActivityScope
    public Context provideContext() {
        return mActivity;
    }
}
