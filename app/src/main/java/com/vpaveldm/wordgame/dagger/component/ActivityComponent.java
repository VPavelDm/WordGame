package com.vpaveldm.wordgame.dagger.component;

import com.vpaveldm.wordgame.dagger.module.CiceroneModule;
import com.vpaveldm.wordgame.dagger.module.DataLayerModule;
import com.vpaveldm.wordgame.dagger.module.GoogleAuthModule;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.presentationLayer.view.activity.MainActivity;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.add_deck.AddDeckFragment;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.choose_deck.ChooseDeckFragment;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.logging.LoggingFragment;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.menu.MenuFragment;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.choose_deck.DeckRecyclerAdapter;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.play.PlayFragment;
import com.vpaveldm.wordgame.presentationLayer.viewModel.AddDeckViewModel;
import com.vpaveldm.wordgame.presentationLayer.viewModel.ChooseDeckViewModel;
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
    void inject(ChooseDeckViewModel chooseDeckViewModel);
    void inject(ChooseDeckFragment chooseDeckFragment);
    void inject(AddDeckFragment addDeckFragment);
    void inject(AddDeckViewModel addDeckViewModel);
    void inject(DeckRecyclerAdapter.ViewHolder viewHolder);
    void inject(PlayFragment playFragment);
    void inject(PlayViewModel playViewModel);
}
