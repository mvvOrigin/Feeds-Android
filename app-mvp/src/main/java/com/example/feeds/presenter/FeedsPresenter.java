package com.example.feeds.presenter;

import com.example.feeds.R;
import com.example.feeds.model.ApiService;
import com.example.feeds.dagger.MainApplication;
import com.example.feeds.util.Utils;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FeedsPresenter implements FeedsContract.Presenter {

    @Inject
    ApiService apiService;

    private FeedsContract.View view;

    private CompositeSubscription subscriptions;

    public FeedsPresenter(FeedsContract.View view) {
        this.view = view;
        MainApplication.getComponent().inject(this);
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void onRefresh() {
        view.showRefreshing(true);

        subscriptions.add(
                apiService.getFeeds()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(feeds -> {
                            view.showRefreshing(false);
                            view.showFeeds(feeds);
                            Utils.snack(view.getContext(),R.string.feeds_updated,false);

                        }, throwable -> {
                            view.showRefreshing(false);
                            Utils.snack(view.getContext(),R.string.feeds_error,false);
                        }));
    }

    @Override
    public void onDetach() {
        if (subscriptions.hasSubscriptions())
            subscriptions.unsubscribe();

        subscriptions = null;
        view = null;
    }
}
