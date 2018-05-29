package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.activity.MainActivity;
import com.vpaveldm.wordgame.dagger.module.ActivityModule;
import com.vpaveldm.wordgame.dagger.module.CiceroneModule;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.logging.LoggingFragment;
import com.vpaveldm.wordgame.menu.MenuFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {ActivityModule.class, CiceroneModule.class})
@ActivityScope
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(LoggingFragment fragment);
    void inject(MenuFragment fragment);
}
