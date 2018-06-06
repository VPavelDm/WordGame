package com.vpaveldm.wordgame.presentationLayer.view.activity;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.FragmentManager;

import com.vpaveldm.wordgame.dagger.module.GoogleAuthModule;
import com.vpaveldm.wordgame.presentationLayer.Application;
import com.vpaveldm.wordgame.dagger.component.ActivityComponent;
import com.vpaveldm.wordgame.dagger.component.AppComponent;
import com.vpaveldm.wordgame.dagger.module.CiceroneModule;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;

public class ActivityComponentManager implements LifecycleObserver {

    private MainActivity mActivity;

    @Inject
    Navigator mNavigator;

    ActivityComponentManager(MainActivity activity) {
        this.mActivity = activity;
    }

    private static ActivityComponent sActivityComponent;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void initDagger() {
        if (sActivityComponent == null) {
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            CiceroneModule ciceroneModule = new CiceroneModule(fragmentManager);
            GoogleAuthModule authModule = new GoogleAuthModule(mActivity);
            AppComponent appComponent = Application.getAppComponent();
            sActivityComponent = appComponent.plusActivityComponent(ciceroneModule, authModule);
        }
        sActivityComponent.inject(mActivity);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clearDagger() {
        mActivity = null;
        sActivityComponent = null;
    }

    public static ActivityComponent getActivityComponent() {
        return sActivityComponent;
    }
}
