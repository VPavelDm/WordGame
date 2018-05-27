package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.activity.MainActivity;
import com.vpaveldm.wordgame.dagger.module.ActivityModule;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;

import dagger.Subcomponent;

@Subcomponent(modules = {ActivityModule.class})
@ActivityScope
public interface ActivityComponent {
    void inject(MainActivity activity);
}
