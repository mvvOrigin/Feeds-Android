package com.example.feeds.dagger;

import com.example.feeds.presenter.FeedsPresenter;
import com.example.feeds.model.ApiModule;
import com.example.feeds.presenter.ItemPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class, ApiModule.class})
public interface MainComponent {

     void inject(FeedsPresenter object);

     void inject(ItemPresenter object);
}

