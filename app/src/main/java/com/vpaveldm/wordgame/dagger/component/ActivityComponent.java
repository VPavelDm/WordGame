package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.activity.MainActivity;
import com.vpaveldm.wordgame.dagger.module.CiceroneModule;
import com.vpaveldm.wordgame.dagger.module.GoogleAuthModule;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;

import dagger.Subcomponent;

@Subcomponent(modules = {CiceroneModule.class})
@ActivityScope
public interface ActivityComponent {
    void inject(MainActivity activity);

    LoggingComponent plus(GoogleAuthModule module);
}
