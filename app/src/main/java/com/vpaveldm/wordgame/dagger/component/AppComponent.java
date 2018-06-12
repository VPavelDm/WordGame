package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.dagger.module.AppContextModule;
import com.vpaveldm.wordgame.dagger.module.CiceroneModule;
import com.vpaveldm.wordgame.dagger.module.FirebaseAuthModule;
import com.vpaveldm.wordgame.dagger.module.GoogleAuthModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {FirebaseAuthModule.class, AppContextModule.class})
@Singleton
public interface AppComponent {
    ActivityComponent plusActivityComponent(CiceroneModule ciceroneModule, GoogleAuthModule authModule);
}
