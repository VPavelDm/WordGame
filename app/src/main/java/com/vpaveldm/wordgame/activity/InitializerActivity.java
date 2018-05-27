package com.vpaveldm.wordgame.activity;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.vpaveldm.wordgame.Application;
import com.vpaveldm.wordgame.dagger.component.ActivityComponent;
import com.vpaveldm.wordgame.dagger.module.ActivityModule;

public class InitializerActivity implements LifecycleObserver {

    private Context sContext;

    InitializerActivity(Context sContext) {
        this.sContext = sContext;
    }

    private static ActivityComponent sActivityComponent;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init() {
        if (sActivityComponent == null) {
            ActivityModule module = new ActivityModule(sContext);
            sActivityComponent = Application.getAppComponent().plusActivityComponent(module);
        }
        sActivityComponent.inject((MainActivity) sContext);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clear() {
        sContext = null;
        sActivityComponent = null;
    }
}
