package com.vpaveldm.wordgame.dagger.module;

import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.ILoggingRepository;
import com.vpaveldm.wordgame.dataLayer.repository.LoggingRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class LoggingModule {

    @Provides
    @ActivityScope
    public ILoggingRepository provideLoggingRepository(LoggingRepositoryImpl repository){
        return repository;
    }

}
