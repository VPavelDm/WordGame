package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.adapterLayer.viewModel.AddDeckViewModel;
import com.vpaveldm.wordgame.adapterLayer.viewModel.ChooseDeckViewModel;
import com.vpaveldm.wordgame.adapterLayer.viewModel.LoggingViewModel;
import com.vpaveldm.wordgame.adapterLayer.viewModel.PlayViewModel;
import com.vpaveldm.wordgame.adapterLayer.viewModel.RatingViewModel;
import com.vpaveldm.wordgame.dagger.module.CiceroneModule;
import com.vpaveldm.wordgame.dagger.module.DataLayerModule;
import com.vpaveldm.wordgame.dagger.module.GoogleAuthModule;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.uiLayer.view.activity.MainActivity;
import com.vpaveldm.wordgame.uiLayer.view.fragments.add_deck.AddDeckFragment;
import com.vpaveldm.wordgame.uiLayer.view.fragments.choose_deck.ChooseDeckFragment;
import com.vpaveldm.wordgame.uiLayer.view.fragments.choose_deck.DeckAdapter;
import com.vpaveldm.wordgame.uiLayer.view.fragments.logging.LoggingFragment;
import com.vpaveldm.wordgame.uiLayer.view.fragments.menu.MenuFragment;
import com.vpaveldm.wordgame.uiLayer.view.fragments.play.PlayFragment;
import com.vpaveldm.wordgame.uiLayer.view.fragments.rating.RatingFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {CiceroneModule.class, DataLayerModule.class, GoogleAuthModule.class})
@ActivityScope
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(MenuFragment menuFragment);

    void inject(LoggingFragment loggingFragment);

    void inject(LoggingViewModel loggingViewModel);

    void inject(ChooseDeckViewModel chooseDeckViewModel);

    void inject(ChooseDeckFragment chooseDeckFragment);

    void inject(AddDeckFragment addDeckFragment);

    void inject(AddDeckViewModel addDeckViewModel);

    void inject(PlayFragment playFragment);

    void inject(PlayViewModel playViewModel);

    void inject(RatingViewModel ratingViewModel);

    void inject(RatingFragment ratingFragment);

    void inject(DeckAdapter.ViewHolder viewHolder);
}
