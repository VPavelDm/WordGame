package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.dagger.module.ActivityModule;
import com.vpaveldm.wordgame.dagger.module.AppContextModule;
import com.vpaveldm.wordgame.dagger.module.FirebaseAuthModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {FirebaseAuthModule.class, AppContextModule.class})
@Singleton
public interface AppComponent {
    ActivityComponent plusActivityComponent(ActivityModule activityModule);
}
