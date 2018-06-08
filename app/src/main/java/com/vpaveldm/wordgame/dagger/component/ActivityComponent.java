package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.dagger.module.CiceroneModule;
import com.vpaveldm.wordgame.dagger.module.DataLayerModule;
import com.vpaveldm.wordgame.dagger.module.GoogleAuthModule;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.presentationLayer.view.activity.MainActivity;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.logging.LoggingFragment;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.menu.MenuFragment;
import com.vpaveldm.wordgame.presentationLayer.viewModel.LoggingViewModel;
import com.vpaveldm.wordgame.presentationLayer.viewModel.PlayViewModel;

import dagger.Subcomponent;

@Subcomponent(modules = {CiceroneModule.class, DataLayerModule.class, GoogleAuthModule.class})
@ActivityScope
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(MenuFragment menuFragment);
    void inject(LoggingFragment loggingFragment);
    void inject(LoggingViewModel loggingViewModel);
    void inject(PlayViewModel playViewModel);
}
