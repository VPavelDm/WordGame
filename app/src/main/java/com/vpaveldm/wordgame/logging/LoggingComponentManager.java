package com.vpaveldm.wordgame.logging;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.vpaveldm.wordgame.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.dagger.component.LoggingComponent;

public class LoggingComponentManager implements LifecycleObserver {

    private LoggingFragment mLoggingFragment;

    private static LoggingComponent sLoggingComponent;

    public LoggingComponentManager(LoggingFragment loggingFragment) {
        mLoggingFragment = loggingFragment;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void initDagger() {
        if (sLoggingComponent == null) {
            sLoggingComponent = ActivityComponentManager.getActivityComponent().plus();
        }
        sLoggingComponent.inject(mLoggingFragment);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clearDagger() {
        mLoggingFragment = null;
        sLoggingComponent = null;
    }
}
