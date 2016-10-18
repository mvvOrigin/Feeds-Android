package com.example.feeds.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class MainModule {

    private Application application;

    MainModule(MainApplication application) {
        this.application = application;
    }

    @Provides @Singleton
    Context context() {
        return application.getApplicationContext();
    }
}

