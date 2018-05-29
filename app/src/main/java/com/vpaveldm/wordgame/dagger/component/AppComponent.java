package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.dagger.module.ActivityModule;
import com.vpaveldm.wordgame.dagger.module.FirebaseAuthModule;
import com.vpaveldm.wordgame.logging.LoggingFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {FirebaseAuthModule.class})
@Singleton
public interface AppComponent {
    ActivityComponent plusActivityComponent(ActivityModule activityModule);
}
