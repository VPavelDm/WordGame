package com.vpaveldm.wordgame;


import com.vpaveldm.wordgame.dagger.component.AppComponent;
import com.vpaveldm.wordgame.dagger.component.DaggerAppComponent;
import com.vpaveldm.wordgame.dagger.module.FirebaseAuthModule;

public class Application extends android.app.Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent.builder()
                .firebaseAuthModule(new FirebaseAuthModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
