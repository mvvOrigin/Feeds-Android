package com.example.feeds.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.example.feeds.R;
import com.example.feeds.dagger.MainApplication;
import com.example.feeds.model.ApiService;
import com.example.feeds.model.Feed;
import com.example.feeds.util.Utils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FeedsViewModel extends BaseObservable {

    @Inject
    ApiService api;

    private Context context;

    private CompositeSubscription subscriptions;

    private ObservableField<List<Feed>> feeds;
    private ObservableBoolean isRefreshing;

    public FeedsViewModel(Context context) {
        this.context = context;
        MainApplication.getComponent().inject(this);

        this.subscriptions = new CompositeSubscription();
        this.feeds = new ObservableField<>(Collections.emptyList());
        this.isRefreshing = new ObservableBoolean(false);
    }

    public ObservableField<List<Feed>> getFeeds() {
        return feeds;
    }

    public ObservableBoolean getIsRefreshing() {
        return isRefreshing;
    }

    public void onRefresh() {
        isRefreshing.set(true);

        subscriptions.add(
                api.getFeeds()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {

                            isRefreshing.set(false);
                            feeds.set(list);
                            feeds.notifyChange();
                            Utils.snack(context,R.string.feeds_updated,false);

                        }, throwable -> {

                            isRefreshing.set(false);
                            Utils.snack(context,R.string.feeds_error,false);
                        })
        );
    }

    public void onDetach() {
        if (subscriptions.hasSubscriptions())
            subscriptions.unsubscribe();

        subscriptions = null;
        isRefreshing = null;
        feeds = null;
        context = null;
    }
}
