package com.example.feeds.dagger;

import com.example.feeds.model.ApiModule;
import com.example.feeds.viewmodel.FeedsViewModel;
import com.example.feeds.viewmodel.ItemViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class, ApiModule.class})
public interface MainComponent {

     void inject(FeedsViewModel object);

     void inject(ItemViewModel object);
}

