package com.vpaveldm.wordgame.dagger.module;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IYandexTranslateRepository;
import com.vpaveldm.wordgame.dataLayer.interfaces.IFirebaseRepository;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;
import com.vpaveldm.wordgame.dataLayer.repository.YandexTranslateRepositoryImpl;
import com.vpaveldm.wordgame.dataLayer.repository.FirebaseRepositoryImpl;
import com.vpaveldm.wordgame.dataLayer.repository.LoggingRepositoryImpl;
import com.vpaveldm.wordgame.dataLayer.repository.PlayRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class DataLayerModule {

    @Provides
    @ActivityScope
    public ILoggingRepository provideLoggingRepository(LoggingRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @ActivityScope
    public IFirebaseRepository provideChooseDeckRepository(FirebaseRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @ActivityScope
    public IYandexTranslateRepository provideAddDeckRepository(YandexTranslateRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @ActivityScope
    public IPlayRepository providePlayRepository(PlayRepositoryImpl repository){
        return repository;
    }

}
