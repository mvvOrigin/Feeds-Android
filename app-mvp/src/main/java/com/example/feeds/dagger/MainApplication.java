package com.example.feeds.dagger;

import android.app.Application;

public class MainApplication extends Application {

    private static MainComponent component;

    public static MainComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build();
    }
}

