package com.vpaveldm.wordgame.activity;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.vpaveldm.wordgame.Application;
import com.vpaveldm.wordgame.dagger.component.ActivityComponent;
import com.vpaveldm.wordgame.dagger.module.ActivityModule;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;

public class DaggerActivityInitializer implements LifecycleObserver {

    private Context sContext;

    @Inject
    Navigator mNavigator;

    DaggerActivityInitializer(Context sContext) {
        this.sContext = sContext;
    }

    private static ActivityComponent sActivityComponent;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void initDagger() {
        if (sActivityComponent == null) {
            ActivityModule module = new ActivityModule(sContext);
            sActivityComponent = Application.getAppComponent().plusActivityComponent(module);
        }
        sActivityComponent.inject((MainActivity) sContext);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clearDagger() {
        sContext = null;
        sActivityComponent = null;
    }
}
