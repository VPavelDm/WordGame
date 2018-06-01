package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.dagger.scope.FragmentScope;
import com.vpaveldm.wordgame.fragments.logging.LoggingFragment;

import dagger.Subcomponent;

@Subcomponent()
@FragmentScope
public interface LoggingComponent {
    void inject(LoggingFragment fragment);
}
