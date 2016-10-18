package com.example.feeds.model;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides @Singleton
    ApiService providesApiService(Context context){
        return new FakeApiService(context);
    }
}
