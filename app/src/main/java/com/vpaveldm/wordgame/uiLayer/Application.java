package com.vpaveldm.wordgame.uiLayer;


import com.vpaveldm.wordgame.dagger.component.AppComponent;
import com.vpaveldm.wordgame.dagger.component.DaggerAppComponent;
import com.vpaveldm.wordgame.dagger.module.AppContextModule;
import com.vpaveldm.wordgame.dagger.module.FirebaseAuthModule;

public class Application extends android.app.Application {

    private static AppComponent sAppComponent;
    public static final String TAG = "wordGameTAG";

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent.builder()
                .firebaseAuthModule(new FirebaseAuthModule())
                .appContextModule(new AppContextModule(getApplicationContext()))
                .build();
    }


    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
