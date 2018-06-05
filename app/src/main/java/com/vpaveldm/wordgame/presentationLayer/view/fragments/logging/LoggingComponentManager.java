package com.vpaveldm.wordgame.presentationLayer.view.fragments.logging;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

public class LoggingComponentManager implements LifecycleObserver {

    private LoggingFragment mLoggingFragment;

    LoggingComponentManager(LoggingFragment loggingFragment) {
        mLoggingFragment = loggingFragment;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void initDagger() {
        ActivityComponentManager.getActivityComponent().inject(mLoggingFragment);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clearDagger() {
        mLoggingFragment = null;
    }
}
