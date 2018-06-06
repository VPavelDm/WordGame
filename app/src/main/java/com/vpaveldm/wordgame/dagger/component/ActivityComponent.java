package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.dagger.module.CiceroneModule;
import com.vpaveldm.wordgame.dagger.module.GoogleAuthModule;
import com.vpaveldm.wordgame.dagger.module.LoggingModule;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.presentationLayer.view.activity.MainActivity;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.logging.LoggingFragment;
import com.vpaveldm.wordgame.presentationLayer.viewModel.LoggingViewModel;

import dagger.Subcomponent;

@Subcomponent(modules = {CiceroneModule.class, LoggingModule.class, GoogleAuthModule.class})
@ActivityScope
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(LoggingFragment loggingFragment);
    void inject(LoggingViewModel loggingViewModel);
}
