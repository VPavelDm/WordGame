package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.dagger.module.AppContextModule;
import com.vpaveldm.wordgame.dagger.module.CiceroneModule;
import com.vpaveldm.wordgame.dagger.module.FirebaseAuthModule;
import com.vpaveldm.wordgame.dagger.module.GoogleAuthModule;
import com.vpaveldm.wordgame.dagger.module.YandexTranslateModule;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.rating.UserRecyclerAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {FirebaseAuthModule.class, AppContextModule.class, YandexTranslateModule.class})
@Singleton
public interface AppComponent {
    ActivityComponent plusActivityComponent(CiceroneModule ciceroneModule, GoogleAuthModule authModule);
    void inject(UserRecyclerAdapter.ViewHolder viewHolder);
}
