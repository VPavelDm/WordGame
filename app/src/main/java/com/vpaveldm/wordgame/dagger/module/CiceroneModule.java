package com.vpaveldm.wordgame.dagger.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.add_deck.AddDeckFragment;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.logging.LoggingFragment;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.menu.MenuFragment;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.choose_deck.ChooseDeckFragment;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.play.PlayFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

@Module
public class CiceroneModule {

    private final FragmentManager mManager;

    public CiceroneModule(FragmentManager manager) {
        mManager = manager;
    }

    @Provides
    @ActivityScope
    public Cicerone<Router> provideCicerone(Navigator navigator) {
        Log.i("ciceroneTAG", "provideCicerone");
        Cicerone<Router> cicerone = Cicerone.create();
        cicerone.getNavigatorHolder().setNavigator(navigator);
        return cicerone;
    }

    @Provides
    @ActivityScope
    public Router provideRouter(Cicerone<Router> cicerone) {
        return cicerone.getRouter();
    }

    @Provides
    @ActivityScope
    public Navigator provideNavigator(@Named("Application") final Context context) {
        return new SupportFragmentNavigator(mManager, R.id.fragmentContainer) {
            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                switch (screenKey) {
                    case "LOGIN_FRAGMENT": {
                        return new LoggingFragment();
                    }
                    case "MENU_FRAGMENT": {
                        return new MenuFragment();
                    }
                    case "CHOOSE_DECK_FRAGMENT": {
                        return new ChooseDeckFragment();
                    }
                    case "ADD_DECK_FRAGMENT": {
                        return new AddDeckFragment();
                    }
                    case "PLAY_FRAGMENT": {
                        if (data instanceof String) {
                            return PlayFragment.newInstance((String) data);
                        }
                    }
                    default: {
                        String error = context.getString(R.string.illegal_argument, "screen key");
                        throw new IllegalArgumentException(error);
                    }
                }
            }

            @Override
            protected void showSystemMessage(String message) {
                String error = context.getString(
                        R.string.unsupported_operation,
                        "Show system message "
                );
                throw new UnsupportedOperationException(error);
            }

            @Override
            protected void exit() {
                ((AppCompatActivity) context).finish();
            }
        };
    }
}
