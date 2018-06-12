package com.vpaveldm.wordgame.dagger.module;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IAddDeckRepository;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.dataLayer.interfaces.IChooseDeckRepository;
import com.vpaveldm.wordgame.dataLayer.repository.AddDeckRepositoryImpl;
import com.vpaveldm.wordgame.dataLayer.repository.ChooseDeckRepositoryImpl;
import com.vpaveldm.wordgame.dataLayer.repository.LoggingRepositoryImpl;

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
    public IChooseDeckRepository providePlayRepository(ChooseDeckRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @ActivityScope
    public IAddDeckRepository provideAddDeckRepository(AddDeckRepositoryImpl repository) {
        return repository;
    }

}
